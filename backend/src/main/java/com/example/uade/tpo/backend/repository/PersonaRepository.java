package com.example.uade.tpo.backend.repository;


import com.example.uade.tpo.backend.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona,Long> {
}
