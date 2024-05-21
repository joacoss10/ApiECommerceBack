package com.example.uade.tpo.backend.repository;


import com.example.uade.tpo.backend.models.Orden;
import com.example.uade.tpo.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orden,Long> {
    List<Orden> findByUsuario(User user);
}
