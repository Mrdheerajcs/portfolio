package com.auspicius.Repository;

import com.auspicius.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Override
    Optional<User> findById(Integer Integer);
    @Query("SELECT u.status FROM User u WHERE u.id = :id")
    boolean isUserActiveById(@Param("id") Integer id);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}

