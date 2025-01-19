package com.auspicius.Repository;

import com.auspicius.Entity.Experience;
import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    List<Experience> findByPortfolioId(Portfolio portfolio);
    List<Experience> findByPortfolioIdAndAndStatus(Portfolio portfolio, Boolean status);
}
