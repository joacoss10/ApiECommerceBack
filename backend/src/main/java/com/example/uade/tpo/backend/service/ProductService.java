package com.example.uade.tpo.backend.service;


import com.example.uade.tpo.backend.auxiliar.ProductResponse;
import com.example.uade.tpo.backend.auxiliar.Producto;
import com.example.uade.tpo.backend.auxiliar.img.ImageResponse;
import com.example.uade.tpo.backend.models.Image;
import com.example.uade.tpo.backend.models.Product;
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

            product.setVendedor(userRepository.findByUsername(producto.getUsername_vendedor()));
            product.setNombre(producto.getNombre());
            product.setDescripcion(producto.getDescripcion());
            product.setCategoria(producto.getCategoria());
            product.setStockDisponible(producto.getStockDisponible());
            product.setPrecio(producto.getPrecio());

            //System.out.println(product.getId_producto());
            //product.setImagenURL(null);
            product.setImageList(null);
            productRepository.save(product);
            //System.out.println("hasta aca llegamos " + product.getId_producto());

            /* 
            for (String url : producto.getImagenURL()){
                Images images = new Images();
                images.setUrl(url);
                images.setProduct(product);
                imagesRepository.save(images);
            }
            */
            for (MultipartFile iMultipartFile : producto.getFiles()){
                Image image = new Image();
                
                byte[] bytes = iMultipartFile.getBytes();
                Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
                
                System.out.println("blob: "+ blob);

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
            
            Slice<Product> productSlice = productRepository.findByVendedor(userRepository.findByUsername(username), pageable);
            
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
}
