package com.example.uade.tpo.backend.controllers;

import com.example.uade.tpo.backend.auxiliar.ProductModel;

import com.example.uade.tpo.backend.models.Cart;
import com.example.uade.tpo.backend.models.CartElement;
import com.example.uade.tpo.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addProductToCart(
            @RequestBody ProductModel productModel,
            @RequestParam(name = "username") String username
    ){

        return cartService.addProductToCart(productModel,username);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editCartItem(@RequestBody CartElement cartElement){
        return cartService.editCartItem(cartElement);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long id){
        return cartService.getCartByUserId(id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCartItem(@RequestParam Long id_item ){
        return cartService.deleteCartItem(id_item);
    }

    //CHECK STOCK
    @GetMapping("/check/{id_cart}")
    public ResponseEntity<String> checkStock(@PathVariable Long id_cart){
        return cartService.checkStock(id_cart);
    }


}
