package com.example.uade.tpo.backend.repository;

import com.example.uade.tpo.backend.models.Cart;
import com.example.uade.tpo.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUsuario(User usuario);
}
