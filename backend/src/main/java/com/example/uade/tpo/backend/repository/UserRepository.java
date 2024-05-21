package com.example.uade.tpo.backend.repository;


import com.example.uade.tpo.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByMail(String mail);
}
