package com.example.uade.tpo.backend.service;


import com.example.uade.tpo.backend.auxiliar.ProductModel;
import com.example.uade.tpo.backend.models.Cart;
import com.example.uade.tpo.backend.models.CartElement;
import com.example.uade.tpo.backend.models.Product;
import com.example.uade.tpo.backend.models.User;
import com.example.uade.tpo.backend.repository.CartElementRepository;
import com.example.uade.tpo.backend.repository.CartRepository;
import com.example.uade.tpo.backend.repository.ProductRepository;
import com.example.uade.tpo.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartElementRepository cartElementRepository;

    public ResponseEntity<String> addProductToCart(ProductModel productModel, String username
    ){
        User user = userRepository.findByUsername(username);
        Optional<Cart> optionalCart = cartRepository.findById(user.getCart().getId());
        if (optionalCart.isPresent()){
            Cart cart = optionalCart.get();

            Optional<Product> optionalProduct = productRepository.findById(productModel.getId());

            if (optionalProduct.isPresent()){
                Product product = optionalProduct.get();
                CartElement cartElement = new CartElement();
                cartElement.setCart(cart);
                cartElement.setCantidad(productModel.getCant());
                cartElement.setProduct(product);

                List<CartElement> cartElementList= cart.getCartElementList();

                cartElementList.add(cartElement);
                cartElementRepository.save(cartElement);

                cart.setCartElementList(cartElementList);
                cartRepository.save(cart);
                return ResponseEntity.ok("Producto añadido al carrito correctamente");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
            }

        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Carrito inexistente");
        }


    }

    public ResponseEntity<Cart> getCartByUserId(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            Cart cart = optionalUser.get().getCart();
            return ResponseEntity.ok(cart);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }


    public ResponseEntity<String> editCartItem(CartElement cartElement){
        Optional<CartElement> cartItemOptional = cartElementRepository.findById(cartElement.getId());
        if (cartItemOptional.isPresent()){
            CartElement cartElementUpdated = cartItemOptional.get();
            cartElementUpdated.setCantidad(cartElement.getCantidad());

            cartElementRepository.save(cartElementUpdated);
            return ResponseEntity.ok("Item actualizado correctamente");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item no encontrado");
        }

    }

    public ResponseEntity<String> deleteCartItem(Long id_item){
        Optional<CartElement> cartElementOptional = cartElementRepository.findById(id_item);
        if (cartElementOptional.isPresent()){

            cartElementRepository.delete(cartElementOptional.get());

            return ResponseEntity.ok("Item eliminado con exito");

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item no encontrado");
        }
    }

    public ResponseEntity<String> checkStock(Long id_cart) {
        Optional<Cart> optionalCart = cartRepository.findById(id_cart);
        if (optionalCart.isPresent()) {
            if (checkCartStock(optionalCart.get())) {
                return ResponseEntity.ok("Todos los productos del carrito cuentan con stock");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No hay suficiente stock para uno o más productos del carrito");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no encontrado");
        }
    }


    public boolean checkCartStock(Cart cart){
        for (CartElement cartElement : cart.getCartElementList()){
            if (cartElement.getCantidad() > cartElement.getProduct().getStockDisponible()){
                return false;
            }
        }
        return true;
    }
}
