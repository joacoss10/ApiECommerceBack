package com.example.uade.tpo.backend.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "Images")
@Data
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id_image;

    @Column (nullable = false)
    private String url;


    @ManyToOne
    @JsonIgnore
    private Product product;

}




