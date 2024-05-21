package com.example.uade.tpo.backend.models;


import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "Orden")
@Data
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User usuario;


    @OneToMany(mappedBy = "orden")
    private List<OrdenElement> ordenElementsList;


}
