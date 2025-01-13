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
import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Portfolio getPortfolioByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for userId: " + userId));
        return portfolioRepository.findByUserId(user)
                .orElseThrow(() -> new RuntimeException("Portfolio not found for userId: " + userId));
    }

    @Override
    public ApiResponse<Portfolio> createPortfolio(PortfolioReq portfolioReq) {
        try {
            validatePortfolioReq(portfolioReq); // Validate incoming request
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

    @Override
    public ApiResponse<Portfolio> getPortfolioById(Integer id) {
        return portfolioRepository.findById(id)
                .map(ResponseUtils::createSuccessResponse)
                .orElseGet(() -> ResponseUtils.createFailureResponse(
                        "Portfolio not found.", HttpStatus.NOT_FOUND.value()
                ));
    }

    @Override
    public ApiResponse<List<Portfolio>> getAllPortfolios() {
        try {
            List<Portfolio> portfolios = portfolioRepository.findAll();
            return ResponseUtils.createSuccessResponse(portfolios);
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse(
                    "An error occurred while retrieving portfolios.",
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

    // Helper method to map PortfolioReq to Portfolio
    // Helper method to map PortfolioReq to Portfolio
    // Helper method to map PortfolioReq to Portfolio
    private Portfolio mapToEntity(PortfolioReq portfolioReq) {
        // Get the User object using the userId from PortfolioReq
        Integer userId = portfolioReq.getUserId(); // Assuming portfolioReq provides the user ID as Integer
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Create a new Portfolio entity and map fields
        Portfolio portfolio = new Portfolio();
        portfolio.setUserId(user); // Set the User object (not the Integer ID)
        portfolio.setTitle(portfolioReq.getTitle());
        portfolio.setTheme(portfolioReq.getTheme());
        portfolio.setIsPublic(portfolioReq.getIsPublic());

        // Set timestamps
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        portfolio.setCreatedOn(currentTime);
        portfolio.setUpdatedOn(currentTime);

        return portfolio;
    }

    // Helper method to validate PortfolioReq
    private void validatePortfolioReq(PortfolioReq portfolioReq) {
        if (portfolioReq.getUserId() == null || portfolioReq.getUserId() == null) {
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
        if (!userRepository.existsById(portfolioReq.getUserId())) {
            throw new IllegalArgumentException("The specified user does not exist.");
        }
    }
}
