package com.example.uade.tpo.backend.service;


import com.example.uade.tpo.backend.auxiliar.CartModel;
import com.example.uade.tpo.backend.auxiliar.ProductModel;
import com.example.uade.tpo.backend.models.*;
import com.example.uade.tpo.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderElementRepository orderElementRepository;
    @Autowired
    CartElementRepository cartElementRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    PagoRespository pagoRespository;
    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<String> createOrder(CartModel cartModel){
        
        Optional<User> compradorOptional = userRepository.findById(cartModel.getId_comprador());
        if (compradorOptional.isPresent()){
            if (!checkStock(cartModel.getProductList())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No hay stock de todos los productos");
            }
            Orden orden = new Orden();

            Pago pago = new Pago();
            pago = cartModel.getPago();

            pagoRespository.save(pago);

            orden.setPago(pago);

            orden.setUsuario(compradorOptional.get());
            
            orden = orderRepository.save(orden);

            Optional<Product> productOptional;
            OrdenElement orderElement;
            for (ProductModel productModel : cartModel.getProductList()){
                
                orderElement = new OrdenElement();
                productOptional = productRepository.findById(productModel.getId());

                if (productOptional.isPresent()){
                    Product producto = productOptional.get();

                    orderElement.setProduct(producto);
                    orderElement.setCantidad(productModel.getCant());
                    orderElement.setOrden(orden);

                    orderElementRepository.save(orderElement);

                    producto.setStockDisponible(producto.getStockDisponible() - productModel.getCant());
                    productRepository.save(producto);

                }            
                
            }
            return ResponseEntity.ok("Orden creada con exito");
        }
        return ResponseEntity.notFound().build();

        
    }





    public ResponseEntity<List<Orden>> getOrderByUserId(long id_user){

        Optional<User> optionalUser = userRepository.findById(id_user);
        if (optionalUser.isPresent()){
            List<Orden> ordenList = optionalUser.get().getOrdenList();

            return ResponseEntity.ok(ordenList);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    private boolean checkStock(List<ProductModel> productModels){
        Optional<Product> optionalProduct;
        for (ProductModel productModel : productModels){
            optionalProduct = productRepository.findById(productModel.getId());
            if(optionalProduct.isPresent()){
                if (optionalProduct.get().getStockDisponible() <= productModel.getCant()){
                    System.out.println("mal stock");
                    return false;
                }
            }else return false;
        }
        return true;
    }
}
