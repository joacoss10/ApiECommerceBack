package com.example.uade.tpo.backend.service.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.uade.tpo.backend.auxiliar.ProductResponse;
import com.example.uade.tpo.backend.auxiliar.Producto;
import com.example.uade.tpo.backend.models.Product;

public interface ProductServiceInterface {

    //CREATE PRODUCT

    public ResponseEntity<String> createProduct(Producto producto);

    //GET PRODUCT

    public ResponseEntity<List<ProductResponse>> getProducts(String categoria, int page, int pageSize, double min, double max) throws IOException, SQLException;

    public ResponseEntity<List<ProductResponse>> getProductsByUsername(String username, int page, int pageSize) throws IOException, SQLException;

    //EDIT PRODUCT
    
    public ResponseEntity<String>  editStockProduct(Product product);

    public ResponseEntity<String> addImage(Long id, MultipartFile file) throws SerialException, SQLException, IOException;


    //ELIMINAR POR ID

    public ResponseEntity<String>  deleteProd(Long id);


    public ResponseEntity<String> deleteImage(Long id);




    public ResponseEntity<Integer> getPages(String categoria, int pageSize, double min, double max) throws IOException, SQLException;

    
    
}
