package com.example.uade.tpo.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Table(name = "User")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String mail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @JsonIgnore // Ignorar esta propiedad al serializar
    @OneToMany(mappedBy = "vendedor")
    private List<Product> productList;
    @JsonIgnore
    @OneToMany(mappedBy = "usuario")            //con mappedby apunto a la variable de la otra tabla que se relaciona con esta    y en esa variable le pongo @JoinColumn
    private List<Orden> ordenList;
    


}
