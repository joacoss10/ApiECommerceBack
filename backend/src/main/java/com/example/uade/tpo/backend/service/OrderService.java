package com.example.uade.tpo.backend.service;


import com.example.uade.tpo.backend.auxiliar.OrderElementResponse;
import com.example.uade.tpo.backend.auxiliar.OrderResponse;
import com.example.uade.tpo.backend.auxiliar.cart.CartModel;
import com.example.uade.tpo.backend.auxiliar.cart.ProductModel;
import com.example.uade.tpo.backend.models.*;
import com.example.uade.tpo.backend.repository.*;
import com.example.uade.tpo.backend.service.interfaces.OrderServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements OrderServiceInterface{

    
    @Autowired
    ProductService productService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderElementRepository orderElementRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PagoRespository pagoRespository;
    @Autowired
    ProductRepository productRepository;
    

    public ResponseEntity<String> createOrder(CartModel cartModel){
        
        Optional<User> compradorOptional = userRepository.findByUsername(cartModel.getUsername_comprador());
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

            orden.setEntregado(false);
            
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

    





    public ResponseEntity<List<OrderResponse>> getOrderByUserId(String username) throws IOException, SQLException{

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()){
            List<Orden> ordenList = optionalUser.get().getOrdenList();
            List<OrderResponse> orderResponses = new ArrayList<>();
            for (Orden orden : ordenList){
                orderResponses.add(convertOrderResponse(orden));
            }

            return ResponseEntity.ok(orderResponses);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    public boolean checkStock(List<ProductModel> productModels){
        Optional<Product> optionalProduct;
        for (ProductModel productModel : productModels){
            optionalProduct = productRepository.findById(productModel.getId());
            if(optionalProduct.isPresent()){
                if (optionalProduct.get().getStockDisponible() < productModel.getCant()){
                    return false;
                }
            }else return false;
        }
        return true;
    }

    public OrderResponse convertOrderResponse(Orden orden) throws IOException, SQLException{
        List<OrderElementResponse> orderElementResponses = new ArrayList<>();
        for (OrdenElement ordenElement : orden.getOrdenElementsList()){
            orderElementResponses.add(OrderElementResponse.builder()
            .id(ordenElement.getId())
            .cantidad(ordenElement.getCantidad())
            .productResponse(productService.convertProduct(ordenElement.getProduct()))
            .build());
        }
        

        return OrderResponse.builder()
            .id(orden.getId())
            .pago(orden.getPago())
            .usuario(orden.getUsuario())
            .ordenElementsList(orderElementResponses)
            .entregado(orden.getEntregado())
            .build();
    }

    @Override
    public ResponseEntity<String> setEntregadoTrue(long id) {
        Optional<Orden> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()){
            Orden orden = optionalOrder.get();
            orden.setEntregado(true);
            orderRepository.save(orden);
            return ResponseEntity.ok().body("Pedido entregado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
    }
}
