package com.flock.springbootbackend.repository;

import com.flock.springbootbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
