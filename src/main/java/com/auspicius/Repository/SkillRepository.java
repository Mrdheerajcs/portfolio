package com.auspicius.Repository;

import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    List<Skill> findByPortfolioId(Portfolio portfolio);

    List<Skill> findByPortfolioIdAndAndStatus(Portfolio portfolio, Boolean status);
}
