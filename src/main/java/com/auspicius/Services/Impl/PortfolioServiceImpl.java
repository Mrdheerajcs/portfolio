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
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Portfolio getPortfolioByUser(Integer userId) {
        return portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found for user: " + userId));
    }

    @Override
    public ApiResponse<Portfolio> createPortfolio(PortfolioReq portfolioReq) {
        try {
            validatePortfolioReq(portfolioReq);

            Portfolio portfolio = mapToEntity(portfolioReq);
            Portfolio savedPortfolio = portfolioRepository.save(portfolio);

            return ResponseUtils.createSuccessResponse(savedPortfolio);
        } catch (IllegalArgumentException e) {
            return ResponseUtils.createFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("Error while saving portfolio: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ApiResponse<Portfolio> getPortfolioById(Integer id) {
        Optional<Portfolio> portfolioOptional = portfolioRepository.findById(id);

        if (portfolioOptional.isEmpty()) {
            return ResponseUtils.createFailureResponse("Portfolio not found.", HttpStatus.NOT_FOUND.value());
        }

        Portfolio portfolio = portfolioOptional.get();

        // Ensure lazy-loaded fields are initialized
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
            return ResponseUtils.createFailureResponse("Error while retrieving portfolios: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ApiResponse<Portfolio> getPortfolioByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        int userId = user.getId();

        Portfolio portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found for user with email: " + email));

        return ResponseUtils.createSuccessResponse(portfolio);
    }


    @Override
    public ApiResponse<Portfolio> updatePortfolio(Integer id, PortfolioReq portfolioReq) {
        return portfolioRepository.findById(id)
                .map(existingPortfolio -> {
                    Portfolio updatedPortfolio = mapToEntity(portfolioReq);
                    updatedPortfolio.setId(id);
                    updatedPortfolio.setCreatedOn(existingPortfolio.getCreatedOn()); // Preserve created timestamp

                    Portfolio savedPortfolio = portfolioRepository.save(updatedPortfolio);
                    return ResponseUtils.createSuccessResponse(savedPortfolio);
                })
                .orElseGet(() -> ResponseUtils.createFailureResponse("Portfolio not found.",
                        HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public ApiResponse<String> deletePortfolio(Integer id) {
        try {
            Optional<Portfolio> portfolioOptional = portfolioRepository.findById(id);

            if (portfolioOptional.isEmpty()) {
                return ResponseUtils.createFailureResponse("Portfolio not found.", HttpStatus.NOT_FOUND.value());
            }

            portfolioRepository.deleteById(id);
            return ResponseUtils.createSuccessResponse("Portfolio deleted successfully.");
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("Error while deleting portfolio: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private Portfolio mapToEntity(PortfolioReq portfolioReq) {
        User user = userRepository.findById(portfolioReq.getUser())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + portfolioReq.getUser()));

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setTitle(portfolioReq.getTitle());
        portfolio.setTheme(portfolioReq.getTheme());
        portfolio.setIsPublic(portfolioReq.getIsPublic());
        portfolio.setStatus(true);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        portfolio.setCreatedOn(currentTime);
        portfolio.setUpdatedOn(currentTime);

        return portfolio;
    }

    private void validatePortfolioReq(PortfolioReq portfolioReq) {
        if (portfolioReq.getUser() == null) {
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
