package com.auspicius.Repository;

import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    Optional<Portfolio> findByUserId(User user);
}
