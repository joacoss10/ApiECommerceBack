package com.example.uade.tpo.backend.controllers;


import com.example.uade.tpo.backend.auxiliar.Producto;
import com.example.uade.tpo.backend.models.Product;
import com.example.uade.tpo.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody Producto producto){
        return productService.createProduct(producto);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(name = "categoria", required = false) String categoria,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize
    ) {
        return productService.getProducts(categoria, page, pageSize);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editStockProduct(@RequestBody Product product) {
        return productService.editStockProduct(product);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProd(@RequestParam Long id) {

        return productService.deleteProd(id);
    }

    @GetMapping("/test")
    public List<Product> testingg(){
        return productService.testingg();
    }

}
