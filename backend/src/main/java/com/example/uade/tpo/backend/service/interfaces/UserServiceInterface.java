package com.example.uade.tpo.backend.service.interfaces;

import org.springframework.http.ResponseEntity;

import com.example.uade.tpo.backend.auxiliar.Login;
import com.example.uade.tpo.backend.models.User;

public interface UserServiceInterface {


    public ResponseEntity<String> altaUsuario(User user);

    public ResponseEntity<String> login(Login login);

    
}
