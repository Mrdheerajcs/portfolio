package com.auspicius.Services;

import com.auspicius.Entity.Portfolio;
import com.auspicius.responce.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface PortfolioService {
    Portfolio getPortfolioByUserId(UUID userId);

    ApiResponse<Portfolio> createPortfolio(Portfolio portfolio);

    ApiResponse<Portfolio> getPortfolioById(UUID id);

    ApiResponse<List<Portfolio>> getAllPortfolios();

    ApiResponse<Portfolio> updatePortfolio(UUID id, Portfolio portfolio);

    ApiResponse<String> deletePortfolio(UUID id);
}
