package com.example.uade.tpo.backend.controllers;


import com.example.uade.tpo.backend.auxiliar.CartModel;
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
    public ResponseEntity<String> createOrder(@RequestBody CartModel cartModel){
        return orderService.createOrder(cartModel);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Orden>> getOrderByUserId(@RequestParam long id_user){
        return orderService.getOrderByUserId(id_user);
    }

}
