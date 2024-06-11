package com.example.uade.tpo.backend.auxiliar.cart;

import java.util.List;

import com.example.uade.tpo.backend.models.Pago;

import lombok.Data;



@Data
public class CartModel {

    private String username_comprador;

    private List<ProductModel> productList;

    private Pago pago;

}
