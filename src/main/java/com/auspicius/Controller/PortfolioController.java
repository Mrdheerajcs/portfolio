package com.auspicius.Controller;

import com.auspicius.Entity.Portfolio;
import com.auspicius.Services.PortfolioService;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.PortfolioReq;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
@CrossOrigin // Allow public access from any domain
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Portfolio>> createPortfolio(@RequestBody @Valid PortfolioReq portfolioReq) {
        ApiResponse<Portfolio> response = portfolioService.createPortfolio(portfolioReq);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<ApiResponse<Portfolio>> getPortfolioById(@PathVariable Integer id) {
        ApiResponse<Portfolio> response = portfolioService.getPortfolioById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Portfolio>>> getAllPortfolios() {
        ApiResponse<List<Portfolio>> response = portfolioService.getAllPortfolios();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updateBy/{id}")
    public ResponseEntity<ApiResponse<Portfolio>> updatePortfolio(@PathVariable Integer id, @RequestBody @Valid PortfolioReq portfolioReq) {
        ApiResponse<Portfolio> response = portfolioService.updatePortfolio(id, portfolioReq);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBy/{id}")
    public ResponseEntity<ApiResponse<String>> deletePortfolio(@PathVariable Integer id) {
        ApiResponse<String> response = portfolioService.deletePortfolio(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getByUserId/{userId}")
    public Portfolio getPortfolio(@PathVariable Integer userId) {
        return portfolioService.getPortfolioByUserId(userId);
    }
}
