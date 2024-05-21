package com.example.uade.tpo.backend.repository;


import com.example.uade.tpo.backend.models.Images;
import com.example.uade.tpo.backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images,Long> {
    Void deleteByProduct(Product product);
}
