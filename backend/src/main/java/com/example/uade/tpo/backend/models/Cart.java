package com.example.uade.tpo.backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;




@Entity
@Table(name = "Cart")
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User usuario;

    @OneToMany(mappedBy = "cart")
    private List<CartElement> cartElementList;
}
