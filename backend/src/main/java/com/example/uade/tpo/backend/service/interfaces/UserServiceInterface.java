package com.example.uade.tpo.backend.service.interfaces;

import org.springframework.http.ResponseEntity;

import com.example.uade.tpo.backend.auxiliar.Login;
import com.example.uade.tpo.backend.auxiliar.LoginResponse;
import com.example.uade.tpo.backend.models.User;

public interface UserServiceInterface {


    public ResponseEntity<LoginResponse> altaUsuario(User user);

    public ResponseEntity<LoginResponse> login(Login login);

    
}
