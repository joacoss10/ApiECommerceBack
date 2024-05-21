package com.example.uade.tpo.backend.repository;


import com.example.uade.tpo.backend.models.Cart;
import com.example.uade.tpo.backend.models.CartElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartElementRepository extends JpaRepository<CartElement,Long> {
    void deleteByCart(Cart cart);
}
