package com.flock.springbootbackend.repository;

import com.flock.springbootbackend.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordTokenRepo extends JpaRepository<PasswordResetToken, Integer> {
}
