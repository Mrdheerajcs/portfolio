package com.auspicius.Repository;

import com.auspicius.Entity.Education;
import com.auspicius.Entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Integer> {

    // Use the portfolioId field directly as an Integer
    List<Education> findByPortfolioId_Id(Integer portfolioId);
}
