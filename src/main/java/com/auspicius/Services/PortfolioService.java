package com.auspicius.Services;

import com.auspicius.Entity.Portfolio;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.PortfolioReq;

import java.util.List;

public interface PortfolioService {
    Portfolio getPortfolioByuser(Integer user);


    ApiResponse<Portfolio> createPortfolio(PortfolioReq portfolioReq);

    ApiResponse<Portfolio> getPortfolioById(Integer id);

    ApiResponse<List<Portfolio>> getAllPortfolios();

    ApiResponse<Portfolio> updatePortfolio(Integer id, PortfolioReq portfolioReq);

    ApiResponse<String> deletePortfolio(Integer id);
}
