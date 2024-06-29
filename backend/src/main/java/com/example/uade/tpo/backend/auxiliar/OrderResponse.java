package com.example.uade.tpo.backend.auxiliar;

import java.util.List;


import com.example.uade.tpo.backend.models.Pago;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    
    private long id;

    private UserResponse usuario;

    private List<OrderElementResponse> ordenElementsList;

    private boolean entregado;

    private Pago pago;
}
