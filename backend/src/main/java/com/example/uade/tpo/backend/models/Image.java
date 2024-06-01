package com.example.uade.tpo.backend.models;


import jakarta.persistence.*;
import lombok.Data;


import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "image_table")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (nullable = false)
    private Blob image;

    @ManyToOne
    @JsonIgnore
    private Product product;
}
