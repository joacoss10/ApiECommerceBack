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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


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

    @PutMapping("/delivered/{id}")
    public ResponseEntity<String> putMethodName(@PathVariable Long id) {        
        return orderService.setEntregadoTrue(id);
    }

}
