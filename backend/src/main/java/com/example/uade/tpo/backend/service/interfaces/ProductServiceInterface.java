package com.example.uade.tpo.backend.service.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.uade.tpo.backend.auxiliar.Producto;
import com.example.uade.tpo.backend.models.Product;

public interface ProductServiceInterface {

    //CREATE PRODUCT

    public ResponseEntity<String> createProduct(Producto producto);

    //GET PRODUCT

    public ResponseEntity<List<Product>> getProducts(String categoria, int page, int pageSize);

    //EDIT PRODUCT
    
    public ResponseEntity<String>  editStockProduct(Product product);


    //ELIMINAR POR ID

    public ResponseEntity<String>  deleteProd(Long id);

    
    
}
