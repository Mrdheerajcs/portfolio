package com.auspicius.Repository;

import com.auspicius.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    Optional<Portfolio> findByUserId(User user);

    @Query("SELECT p FROM Portfolio p WHERE p.id = :id")
    Portfolio findPortfolioById(Integer id);

    @Query("SELECT s FROM Skill s WHERE s.portfolioId.id = :portfolioId AND s.status = true")
    List<Skill> findActiveSkill(Integer portfolioId);

    @Query("SELECT e FROM Education e WHERE e.portfolioId.id = :portfolioId AND e.status = true")
    List<Education> findActiveEducations(Integer portfolioId);

    @Query("SELECT ex FROM Experience ex WHERE ex.portfolioId.id = :portfolioId AND ex.status = true")
    List<Experience> findActiveExperiences(Integer portfolioId);

    @Query("SELECT p FROM Project p WHERE p.portfolioId.id = :portfolioId AND p.status = true")
    List<Project> findActiveProjects(Integer portfolioId);

    @Query("SELECT p.status FROM Portfolio p WHERE p.id = :id")
    boolean isPortfolioActiveById(@Param("id") Integer id);

}
