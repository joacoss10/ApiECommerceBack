package com.example.uade.tpo.backend.auxiliar;

import java.util.List;

import com.example.uade.tpo.backend.auxiliar.img.ImageResponse;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ProductResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;


    private double precio;


    private int stockDisponible;

    private List <ImageResponse> files;

    private String username_vendedor;

}
