package com.example.uade.tpo.backend.controllers;


import com.example.uade.tpo.backend.auxiliar.ProductResponse;
import com.example.uade.tpo.backend.auxiliar.Producto;
import com.example.uade.tpo.backend.models.Product;
import com.example.uade.tpo.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<String> createProduct(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("categoria") String categoria,
            @RequestParam("precio") double precio,
            @RequestParam("stockDisponible") int stockDisponible,
            @RequestParam("username_vendedor") String usernameVendedor,
            @RequestParam("files") List<MultipartFile> file) {
        
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setCategoria(categoria);
        producto.setPrecio(precio);
        producto.setStockDisponible(stockDisponible);
        producto.setUsername_vendedor(usernameVendedor);
        producto.setFiles(file);

        return productService.createProduct(producto);
    }

    @GetMapping("/get")
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(name = "categoria", required = false) String categoria,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize
    ) throws IOException, SQLException {
        return productService.getProducts(categoria, page, pageSize);
    }

    @GetMapping("/getByUsername")
    public ResponseEntity<List<ProductResponse>> getProductsByUsername(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize
    ) throws IOException, SQLException {
        return productService.getProductsByUsername(username, page, pageSize);
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
    public List<ProductResponse> testingg() throws IOException, SQLException{
        return productService.testingg();
    }

}
