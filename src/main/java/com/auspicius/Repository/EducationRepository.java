package com.auspicius.Repository;

import com.auspicius.Entity.Education;
import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Integer> {

    List<Education> findByportfolio(Portfolio portfolio);

}