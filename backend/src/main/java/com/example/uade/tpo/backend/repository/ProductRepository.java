package com.example.uade.tpo.backend.repository;

import com.example.uade.tpo.backend.models.Product;
import com.example.uade.tpo.backend.models.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p FROM Product p")
    Slice<Product> findSlice(Pageable pageable);

    // Método para buscar por categoría con paginación
    Slice<Product> findByCategoria(String categoria, Pageable pageable);

    Slice<Product> findByVendedor(User vendedor, Pageable pageable);
}