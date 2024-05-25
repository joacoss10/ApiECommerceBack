package com.example.uade.tpo.backend.auxiliar;

import java.util.List;

import com.example.uade.tpo.backend.models.Pago;

import lombok.Data;



@Data
public class CartModel {

    private long id_comprador;

    private List<ProductModel> productList;

    private Pago pago;

}
