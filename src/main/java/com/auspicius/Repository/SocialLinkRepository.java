package com.auspicius.Repository;

import com.auspicius.Entity.SocialLink;
import com.auspicius.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SocialLinkRepository extends JpaRepository<SocialLink, Integer> {
    void deleteByUser(User user);
    List<SocialLink> findByUserId(Integer userId);

}

