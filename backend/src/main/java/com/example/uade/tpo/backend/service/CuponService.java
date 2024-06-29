package com.example.uade.tpo.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uade.tpo.backend.models.Cupon;
import com.example.uade.tpo.backend.repository.CuponRepository;
import com.example.uade.tpo.backend.service.interfaces.CuponServiceInterface;

@Service
public class CuponService implements CuponServiceInterface {

    @Autowired
    CuponRepository cuponRepository;


    public String validarCupon(String cupon) {
        Optional<Cupon> OptionalCupon = cuponRepository.findByName(cupon);
        if (OptionalCupon.isPresent()){
            
            return String.valueOf(OptionalCupon.get().getDescuento());
        }
        return "0";
    }


    public void createCupon(String name, Long descuento){

        if (cuponRepository.findByName(name).isEmpty()){
            Cupon cupon = new Cupon();
            cupon.setDescuento(descuento);
            cupon.setName(name);
            
            cuponRepository.save(cupon);
        }
    }
    
}
