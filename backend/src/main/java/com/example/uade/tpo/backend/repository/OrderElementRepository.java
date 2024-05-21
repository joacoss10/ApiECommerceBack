package com.example.uade.tpo.backend.repository;


import com.example.uade.tpo.backend.models.OrdenElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderElementRepository extends JpaRepository <OrdenElement,Long>{
}
