package com.auspicius.Services.Impl;

import com.auspicius.Entity.Portfolio;
import com.auspicius.Repository.PortfolioRepository;
import com.auspicius.Services.PortfolioService;
import com.auspicius.responce.ApiResponse;
import com.auspicius.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public Portfolio getPortfolioByUserId(UUID userId) {
        return portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found for userId: " + userId));
    }

    @Override
    public ApiResponse<Portfolio> createPortfolio(Portfolio portfolio) {
        try {
            Portfolio savedPortfolio = portfolioRepository.save(portfolio);
            return ResponseUtils.createSuccessResponse(savedPortfolio);
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse(
                    "An error occurred while saving the portfolio.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    @Override
    public ApiResponse<Portfolio> getPortfolioById(UUID id) {
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
    public ApiResponse<Portfolio> updatePortfolio(UUID id, Portfolio portfolio) {
        return portfolioRepository.findById(id)
                .map(existingPortfolio -> {
                    portfolio.setId(id);
                    Portfolio updatedPortfolio = portfolioRepository.save(portfolio);
                    return ResponseUtils.createSuccessResponse(updatedPortfolio);
                })
                .orElseGet(() -> ResponseUtils.createFailureResponse(
                        "Portfolio not found.", HttpStatus.NOT_FOUND.value()
                ));
    }

    @Override
    public ApiResponse<String> deletePortfolio(UUID id) {
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

}
