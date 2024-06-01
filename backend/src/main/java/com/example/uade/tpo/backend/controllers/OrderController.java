package com.example.uade.tpo.backend.controllers;


import com.example.uade.tpo.backend.auxiliar.OrderResponse;
import com.example.uade.tpo.backend.auxiliar.cart.CartModel;
import com.example.uade.tpo.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody CartModel cartModel){
        return orderService.createOrder(cartModel);
    }

    @GetMapping("/get")
    public ResponseEntity<List<OrderResponse>> getOrderByUserId(@RequestParam String username) throws IOException, SQLException{
        return orderService.getOrderByUserId(username);
    }

}
