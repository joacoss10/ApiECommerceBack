package com.example.uade.tpo.backend.service.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.uade.tpo.backend.auxiliar.OrderResponse;
import com.example.uade.tpo.backend.auxiliar.cart.CartModel;
import com.example.uade.tpo.backend.auxiliar.cart.ProductModel;
import com.example.uade.tpo.backend.models.Orden;

public interface OrderServiceInterface {



    public ResponseEntity<String> createOrder(CartModel cartModel);

    public ResponseEntity<List<OrderResponse>> getOrderByUserId(String username) throws IOException, SQLException;

    public boolean checkStock(List<ProductModel> productModels);



}
