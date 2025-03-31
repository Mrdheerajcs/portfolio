package com.auspicius.Repository;

import com.auspicius.Entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactMessage, Integer> {
}
