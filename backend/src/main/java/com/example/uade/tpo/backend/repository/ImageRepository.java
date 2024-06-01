package com.example.uade.tpo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.uade.tpo.backend.models.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
