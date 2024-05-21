package com.example.uade.tpo.backend.service;


import com.example.uade.tpo.backend.auxiliar.Login;
import com.example.uade.tpo.backend.models.Cart;
import com.example.uade.tpo.backend.models.User;
import com.example.uade.tpo.backend.repository.CartRepository;
import com.example.uade.tpo.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public ResponseEntity<String> altaUsuario(User user){
        if (userRepository.findByUsername(user.getUsername())!=null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userError");
        } else if (userRepository.findByMail(user.getMail())!=null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("mailError");
        }else {
            try {

                userRepository.save(user);

                Cart cart = new Cart();
                cart.setUsuario(user);
                cartRepository.save(cart);

                return ResponseEntity.ok("ok");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
            }
        }

    }
    public ResponseEntity<String> login(Login login){


        List<User> users = userRepository.findAll();
        for(User user : users){
            if (user.getUsername().equals(login.getUser()) && user.getPassword().equals(login.getPass())){
                System.out.println("TODO BIEN");
                return ResponseEntity.ok("Usuario autenticado correctamente");
            }
        }
        //System.out.println("TODO MAL");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");


    }

    public User testing(String username){

        System.out.println(userRepository.findByUsername(username).getApellido());
        return userRepository.findByUsername(username);
    }


}
