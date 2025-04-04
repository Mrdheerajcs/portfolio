package com.auspicius.Repository;

import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.Project;
import com.auspicius.Entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByportfolio(Portfolio portfolio);
    List<Project> findByportfolioAndAndStatus(Portfolio portfolio, Boolean status);
}
