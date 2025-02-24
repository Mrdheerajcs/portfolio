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
    @Query("SELECT p FROM Portfolio p WHERE p.id = :id")
    Portfolio findPortfolioById(Integer id);

    @Query("SELECT s FROM Skill s WHERE s.portfolio.id = :portfolio AND s.status = true")
    List<Skill> findActiveSkill(Integer portfolio);

    @Query("SELECT e FROM Education e WHERE e.portfolio.id = :portfolio AND e.status = true")
    List<Education> findActiveEducations(Integer portfolio);

    @Query("SELECT ex FROM Experience ex WHERE ex.portfolio.id = :portfolio AND ex.status = true")
    List<Experience> findActiveExperiences(Integer portfolio);

    @Query("SELECT p FROM Project p WHERE p.portfolio.id = :portfolio AND p.status = true")
    List<Project> findActiveProjects(Integer portfolio);

    @Query("SELECT p.status FROM Portfolio p WHERE p.id = :id")
    boolean isPortfolioActiveById(@Param("id") Integer id);

    Optional<Portfolio> findByUserId(Integer userId);


}
