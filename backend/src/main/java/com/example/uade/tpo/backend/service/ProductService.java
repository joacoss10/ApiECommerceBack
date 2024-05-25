package com.example.uade.tpo.backend.service;


import com.example.uade.tpo.backend.auxiliar.Producto;
import com.example.uade.tpo.backend.models.Images;
import com.example.uade.tpo.backend.models.Product;
import com.example.uade.tpo.backend.repository.ImagesRepository;
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


import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceInterface{
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ImagesRepository imagesRepository;

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
            product.setImagenURL(null);
            productRepository.save(product);
            //System.out.println("hasta aca llegamos " + product.getId_producto());


            for (String url : producto.getImagenURL()){
                Images images = new Images();
                images.setUrl(url);
                images.setProduct(product);
                imagesRepository.save(images);
            }
            return ResponseEntity.ok("Producto creado con exito");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrion un error interno");
        }

    }

    //GET PRODUCT

    public ResponseEntity<List<Product>> getProducts(String categoria, int page, int pageSize){
        if (categoria == null || categoria.isEmpty() && page > 0 && pageSize > 0) {
            Pageable pageable = PageRequest.of(page - 1, pageSize);


            Slice<Product> productSlice = productRepository.findSlice(pageable);


            if (productSlice.hasContent()) {

                List<Product> products = productSlice.getContent();
                return ResponseEntity.ok(products);
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

            if (product.getImagenURL()!=null){
                for (Images images : product.getImagenURL()){
                    imagesRepository.delete(images);
                }
            }

            productRepository.delete(product);
            return ResponseEntity.ok("Producto eliminado con éxito");

        } else {
            // Manejar el caso en que el producto no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
    }

    public List<Product> testingg(){
        return productRepository.findAll();
    }
}
