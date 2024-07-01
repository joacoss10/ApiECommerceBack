package com.example.uade.tpo.backend.service;


import com.example.uade.tpo.backend.auxiliar.ProductResponse;
import com.example.uade.tpo.backend.auxiliar.Producto;
import com.example.uade.tpo.backend.auxiliar.img.ImageResponse;
import com.example.uade.tpo.backend.models.Image;
import com.example.uade.tpo.backend.models.Product;
import com.example.uade.tpo.backend.models.User;
import com.example.uade.tpo.backend.repository.ImageRepository;
import com.example.uade.tpo.backend.repository.ProductRepository;
import com.example.uade.tpo.backend.repository.UserRepository;
import com.example.uade.tpo.backend.service.interfaces.ProductServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

@Service
public class ProductService implements ProductServiceInterface{
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ImageRepository imageRepository;

    //CREATE PRODUCT

    public ResponseEntity<String> createProduct(
            Producto producto

    ){
        try {
            Product product = new Product();
            Optional<User> vendedorOptional = userRepository.findByUsername(producto.getUsername_vendedor());
            product.setVendedor(vendedorOptional.get());
            product.setNombre(producto.getNombre());
            product.setDescripcion(producto.getDescripcion());
            product.setCategoria(producto.getCategoria());
            product.setStockDisponible(producto.getStockDisponible());
            product.setPrecio(producto.getPrecio());

            
            product.setImageList(null);
            productRepository.save(product);
            
            for (MultipartFile iMultipartFile : producto.getFiles()){
                Image image = new Image();
                
                byte[] bytes = iMultipartFile.getBytes();
                Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
                
                

                image.setImage(blob);
                image.setProduct(product);

                imageRepository.save(image);
                
            }
            return ResponseEntity.ok("Producto creado con exito");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrion un error interno");
        }

    }

    //GET PRODUCT

    public ResponseEntity<List<ProductResponse>> getProducts(String categoria, int page, int pageSize) throws IOException, SQLException{
        if (categoria == null || categoria.isEmpty() && page > 0 && pageSize > 0) {
            List<ProductResponse> productResponses = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, pageSize);


            Slice<Product> productSlice = productRepository.findSlice(pageable);


            if (productSlice.hasContent()) {

                List<Product> products = productSlice.getContent();
                for (Product product : products){
                    productResponses.add(convertProduct(product));
                }
                return ResponseEntity.ok(productResponses);
            } else {

                return ResponseEntity.notFound().build();
            }
        }else if (page > 0 && pageSize > 0) {
            List<ProductResponse> productResponses = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, pageSize);

            Slice<Product> productSlice = productRepository.findByCategoria(categoria, pageable);

            if (productSlice.hasContent()) {
                List<Product> products = productSlice.getContent();
                for (Product product : products) {
                    productResponses.add(convertProduct(product));
                }
                return ResponseEntity.ok(productResponses);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.badRequest().build();
    }

    //EDIT PRODUCT
    public ResponseEntity<String>  editStockProduct(Product product){
        Optional<Product> optionalUpdateProduct = productRepository.findById(product.getId_producto());

        if (optionalUpdateProduct.isPresent()){
            Product updateProduct = optionalUpdateProduct.get();
            updateProduct.setStockDisponible(product.getStockDisponible());
            productRepository.save(updateProduct);
            return ResponseEntity.ok("Producto editado con exito");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }

    }


    //ELIMINAR POR ID

    public ResponseEntity<String>  deleteProd(Long id){
        Optional<Product> productOptional = productRepository.findById(id);
        System.out.println(productOptional.isPresent());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            

            if (product.getImageList()!=null){
                for (Image image : product.getImageList()){
                    imageRepository.delete(image);
                }
            }

            productRepository.delete(product);
            return ResponseEntity.ok("Producto eliminado con éxito");

        } else {
            // Manejar el caso en que el producto no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
    }

    public List<ProductResponse> testingg() throws IOException, SQLException{
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : productRepository.findAll()){
            productResponses.add(convertProduct(product));
        }

        return productResponses;
    }

    public ProductResponse convertProduct (Product product)  throws IOException, SQLException {

        List<ImageResponse> images = new ArrayList<ImageResponse>();
        

        for (Image image : product.getImageList()){
            images.add(ImageResponse.builder().id(image.getId()).content(Base64.getEncoder().encodeToString(image.getImage().getBytes(1, (int) image.getImage().length()))).build());
        }

        
        return ProductResponse
            .builder()
            .id(product.getId_producto())
            .nombre(product.getNombre())
            .categoria(product.getCategoria())
            .descripcion(product.getDescripcion())
            .precio(product.getPrecio())
            .stockDisponible(product.getStockDisponible())
            .username_vendedor(product.getVendedor().getUsername())
            .files(images)
            //.files(null)
            .build();
    }

    
    public ResponseEntity<List<ProductResponse>> getProductsByUsername(String username, int page, int pageSize)
            throws IOException, SQLException {
        
        if (username != null && !username.isEmpty() && page > 0 && pageSize > 0) {
            List<ProductResponse> productResponses = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            Optional<User> vendedorOptional = userRepository.findByUsername(username);
            Slice<Product> productSlice = productRepository.findByVendedor(vendedorOptional.get(), pageable);
            
            if (productSlice.hasContent()) {
                List<Product> products = productSlice.getContent();
                for (Product product : products) {
                    productResponses.add(convertProduct(product));
                }
                return ResponseEntity.ok(productResponses);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
                
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<String> deleteImage(Long id){
        if (imageRepository.findById(id).isPresent()){
            imageRepository.deleteById(id);
            return ResponseEntity.ok().body("Imagen eliminada con exito");
        } 
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Imagen no encontrada");
    }

    public ResponseEntity<String> addImage(Long id, MultipartFile file) throws SerialException, SQLException, IOException{
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()){
            Product p = product.get();
            Image image = new Image();
                
            byte[] bytes = file.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
                    
            image.setImage(blob);
            image.setProduct(p);
    
            imageRepository.save(image);
            return ResponseEntity.ok().body("Imagen añadida con exito");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
        
    }










    public List<ProductResponse> search(String nombre) throws IOException, SQLException {
        List<ProductResponse> list = new ArrayList<>();
        for (Product product : productRepository.findByNombreContainingIgnoreCase(nombre)){
            list.add(convertProduct(product));
        }
        return list;
    }


    public List<Long> getAllIds(){
        List<Long> list = new ArrayList<>();
        for (Product product : productRepository.findAll()){
            list.add(product.getId_producto());
        }
        return list;
    }


    public ProductResponse getProductById(Long id) throws IOException, SQLException{
        Optional <Product> producOptional = productRepository.findById(id);
        if (producOptional.isPresent()){
            return convertProduct(producOptional.get());
        }
        else{
            return null;
        }
    }
}
