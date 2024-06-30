package com.example.uade.tpo.backend.service;


import com.example.uade.tpo.backend.auxiliar.Login;
import com.example.uade.tpo.backend.auxiliar.LoginResponse;
import com.example.uade.tpo.backend.controllers.config.JwtService;
import com.example.uade.tpo.backend.models.User;
import com.example.uade.tpo.backend.repository.UserRepository;
import com.example.uade.tpo.backend.service.interfaces.UserServiceInterface;

import lombok.RequiredArgsConstructor;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface{
    
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    

    public ResponseEntity<LoginResponse> altaUsuario(User user){
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            //System.out.println(userRepository.findByUsername(user.get);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LoginResponse.builder().accessToken("userError").build());
        } else if (userRepository.findByMail(user.getMail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LoginResponse.builder().accessToken("mailError").build());
        }else {
            try {
                
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                //user.setRole();
                
                userRepository.save(user);

                var jwtToken = jwtService.generateToken(user);

                return ResponseEntity.ok(LoginResponse.builder().accessToken(jwtToken).build());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(LoginResponse.builder().accessToken("error").build());
            }
        }

    }

    public ResponseEntity<LoginResponse> login(Login login){

        //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUser(), login.getPass()));
        //var user = userRepository.findByUsername(login.getUser()).orElseThrow();
        System.out.println(login.getUser() + " - " + login.getPass());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                                login.getUser(),
                                                login.getPass()));
        var user = userRepository.findByUsername(login.getUser())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(LoginResponse.builder().accessToken(jwtToken).build());

    }

    public User testing(String username){

        System.out.println(userRepository.findByUsername(username).get().getApellido());
        return userRepository.findByUsername(username).get();
    }


}
