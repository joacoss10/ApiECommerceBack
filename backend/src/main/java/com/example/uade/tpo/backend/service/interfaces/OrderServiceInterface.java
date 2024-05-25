package com.example.uade.tpo.backend.service.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.uade.tpo.backend.auxiliar.CartModel;
import com.example.uade.tpo.backend.auxiliar.ProductModel;
import com.example.uade.tpo.backend.models.Orden;

public interface OrderServiceInterface {



    public ResponseEntity<String> createOrder(CartModel cartModel);

    public ResponseEntity<List<Orden>> getOrderByUserId(long id_user);

    public boolean checkStock(List<ProductModel> productModels);



}
