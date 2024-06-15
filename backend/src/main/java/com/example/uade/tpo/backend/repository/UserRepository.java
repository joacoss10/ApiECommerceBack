package com.example.uade.tpo.backend.repository;


import com.example.uade.tpo.backend.models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByMail(String mail);
}
