package com.auspicius.Controller;

import com.auspicius.Entity.Portfolio;
import com.auspicius.Services.PortfolioService;
import com.auspicius.responce.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/portfolio")
@CrossOrigin // Allow public access from any domain
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Portfolio>> createPortfolio(@RequestBody @Valid Portfolio portfolio) {
        ApiResponse<Portfolio> response = portfolioService.createPortfolio(portfolio);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Portfolio>> getPortfolioById(@PathVariable UUID id) {
        ApiResponse<Portfolio> response = portfolioService.getPortfolioById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Portfolio>>> getAllPortfolios() {
        ApiResponse<List<Portfolio>> response = portfolioService.getAllPortfolios();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Portfolio>> updatePortfolio(@PathVariable UUID id, @RequestBody @Valid Portfolio portfolio) {
        ApiResponse<Portfolio> response = portfolioService.updatePortfolio(id, portfolio);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePortfolio(@PathVariable UUID id) {
        ApiResponse<String> response = portfolioService.deletePortfolio(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public Portfolio getPortfolio(@PathVariable UUID userId) {
        return portfolioService.getPortfolioByUserId(userId);
    }
}
