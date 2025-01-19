package com.auspicius.Services.Impl;

import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.User;
import com.auspicius.Repository.PortfolioRepository;
import com.auspicius.Repository.UserRepository;
import com.auspicius.Services.PortfolioService;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.PortfolioReq;
import com.auspicius.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Portfolio getPortfolioByuser(Integer user) {
        User users = userRepository.findById(user)
                .orElseThrow(() -> new RuntimeException("User not found for user: " + user));
        return portfolioRepository.findByuser(users)
                .orElseThrow(() -> new RuntimeException("Portfolio not found for user: " + user));
    }

    @Override
    public ApiResponse<Portfolio> createPortfolio(PortfolioReq portfolioReq) {
        try {
            validatePortfolioReq(portfolioReq);
            Portfolio portfolio = mapToEntity(portfolioReq);
            Portfolio savedPortfolio = portfolioRepository.save(portfolio);
            return ResponseUtils.createSuccessResponse(savedPortfolio);
        } catch (IllegalArgumentException e) {
            return ResponseUtils.createFailureResponse(
                    e.getMessage(), HttpStatus.BAD_REQUEST.value()
            );
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse(
                    "An error occurred while saving the portfolio.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    public ApiResponse<Portfolio> getPortfolioById(Integer id) {
        Portfolio portfolio = portfolioRepository.findPortfolioById(id);

        if (portfolio == null) {
            return ResponseUtils.createFailureResponse("Portfolio not found.", HttpStatus.NOT_FOUND.value());
        }

        portfolio.setSkills(new HashSet<>(portfolioRepository.findActiveSkill(id)));
        portfolio.setEducations(new HashSet<>(portfolioRepository.findActiveEducations(id)));
        portfolio.setExperiences(new HashSet<>(portfolioRepository.findActiveExperiences(id)));
        portfolio.setProjects(new HashSet<>(portfolioRepository.findActiveProjects(id)));

        return ResponseUtils.createSuccessResponse(portfolio);
    }

    @Override
    public ApiResponse<List<Portfolio>> getAllPortfolios() {
        try {
            List<Portfolio> portfolios = portfolioRepository.findAll();
            return ResponseUtils.createSuccessResponse(portfolios);
        } catch (Exception e) {
            e.printStackTrace(); // Temporary for debugging
            return ResponseUtils.createFailureResponse(
                    "An error occurred while saving the portfolio: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }

    }

    @Override
    public ApiResponse<Portfolio> updatePortfolio(Integer id, PortfolioReq portfolioReq) {
        return portfolioRepository.findById(id)
                .map(existingPortfolio -> {
                    Portfolio updatedPortfolio = mapToEntity(portfolioReq);
                    updatedPortfolio.setId(id);
                    updatedPortfolio.setStatus(true);
                    Portfolio savedPortfolio = portfolioRepository.save(updatedPortfolio);
                    return ResponseUtils.createSuccessResponse(savedPortfolio);
                })
                .orElseGet(() -> ResponseUtils.createFailureResponse(
                        "Portfolio not found.", HttpStatus.NOT_FOUND.value()
                ));
    }

    @Override
    public ApiResponse<String> deletePortfolio(Integer id) {
        try {
            if (portfolioRepository.existsById(id)) {
                portfolioRepository.deleteById(id);
                return ResponseUtils.createSuccessResponse("Portfolio deleted successfully.");
            } else {
                return ResponseUtils.createFailureResponse(
                        "Portfolio not found.", HttpStatus.NOT_FOUND.value()
                );
            }
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse(
                    "An error occurred while deleting the portfolio.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    private Portfolio mapToEntity(PortfolioReq portfolioReq) {
        Integer user = portfolioReq.getUser();
        User users = userRepository.findById(user)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + user));

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(users);
        portfolio.setTitle(portfolioReq.getTitle());
        portfolio.setTheme(portfolioReq.getTheme());
        portfolio.setIsPublic(portfolioReq.getIsPublic());

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        portfolio.setCreatedOn(currentTime);
        portfolio.setUpdatedOn(currentTime);

        return portfolio;
    }

    private void validatePortfolioReq(PortfolioReq portfolioReq) {
        if (portfolioReq.getUser() == null || portfolioReq.getUser() == null) {
            throw new IllegalArgumentException("User ID is required and cannot be null.");
        }
        if (portfolioReq.getTitle() == null || portfolioReq.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required and cannot be blank.");
        }
        if (portfolioReq.getTheme() == null || portfolioReq.getTheme().isBlank()) {
            throw new IllegalArgumentException("Theme is required and cannot be blank.");
        }
        if (portfolioReq.getIsPublic() == null) {
            throw new IllegalArgumentException("Public status is required and cannot be null.");
        }
        if (!userRepository.existsById(portfolioReq.getUser())) {
            throw new IllegalArgumentException("The specified user does not exist.");
        }
    }
}
