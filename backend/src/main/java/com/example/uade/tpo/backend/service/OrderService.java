package com.example.uade.tpo.backend.service;


import com.example.uade.tpo.backend.models.*;
import com.example.uade.tpo.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    CartService cartService;
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


    public ResponseEntity<String> createOrder(long id_cart){
        Optional<Cart> cartOptional = cartRepository.findById(id_cart);
        if (cartOptional.isPresent()){
            Cart cart = cartOptional.get();
            if (cartService.checkCartStock(cartOptional.get())){
                Orden order = new Orden();
                //SET USER
                order.setUsuario(cartOptional.get().getUsuario());
                orderRepository.save(order);
                //SET ORDER ELEMENTS & DELETE CART ELEMENTS
                for (CartElement cartElement : cart.getCartElementList()){
                    OrdenElement orderElement = new OrdenElement();

                    orderElement.setProduct(cartElement.getProduct());
                    orderElement.setCantidad(cartElement.getCantidad());
                    orderElement.setOrden(order);

                    orderElementRepository.save(orderElement);

                    cartElementRepository.delete(cartElement);
                }


                return ResponseEntity.ok("Orden creada con exito");

            }

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
}
