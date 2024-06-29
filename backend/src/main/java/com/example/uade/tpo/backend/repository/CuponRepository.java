package com.example.uade.tpo.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.uade.tpo.backend.models.Cupon;

@Repository
public interface CuponRepository extends JpaRepository<Cupon,Long> {
    
    Optional<Cupon> findByName(String name);
}
