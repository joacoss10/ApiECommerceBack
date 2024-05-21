package com.example.uade.tpo.backend.controllers;


import com.example.uade.tpo.backend.models.Orden;
import com.example.uade.tpo.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestParam long id_cart){
        return orderService.createOrder(id_cart);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Orden>> getOrderByUserId(@RequestParam long id_user){
        return orderService.getOrderByUserId(id_user);
    }

}
