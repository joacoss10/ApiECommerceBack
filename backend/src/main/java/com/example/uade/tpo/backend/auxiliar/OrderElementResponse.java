package com.example.uade.tpo.backend.auxiliar;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderElementResponse {
    
    private long id;

    private ProductResponse productResponse;

    private int cantidad;

}
