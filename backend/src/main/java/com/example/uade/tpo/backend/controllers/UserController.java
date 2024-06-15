package com.example.uade.tpo.backend.controllers;


import com.example.uade.tpo.backend.auxiliar.Login;
import com.example.uade.tpo.backend.auxiliar.LoginResponse;
import com.example.uade.tpo.backend.models.User;
import com.example.uade.tpo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping("/create")
    @CrossOrigin()
    public ResponseEntity<LoginResponse> altaUsuario(@RequestBody User user){
        return userService.altaUsuario(user);
    }

    @CrossOrigin()
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Login login){
        return userService.login(login);

    }



}
