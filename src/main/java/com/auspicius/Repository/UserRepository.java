package com.auspicius.Repository;

import com.auspicius.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Override
    Optional<User> findById(Integer Integer);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}

