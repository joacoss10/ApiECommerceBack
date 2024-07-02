package com.example.uade.tpo.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.uade.tpo.backend.service.CuponService;


@RestController
@RequestMapping("cupon")
public class CuponController {
    @Autowired
    CuponService cuponService;

    @GetMapping("/check")
    @CrossOrigin()
    public ResponseEntity<String> validarCupon(@RequestParam String cupon) {
        return ResponseEntity.status(HttpStatus.OK).body(cuponService.validarCupon(cupon));
    }

    @PostMapping("/create")
    @CrossOrigin()
    public ResponseEntity<String> createCupon(
        @RequestParam(name = "name") String name,
        @RequestParam(name = "descuento") Long descuento) {
        
            cuponService.createCupon(name, descuento);
        return ResponseEntity.ok().body("Cupon creado");
    }
    
}
