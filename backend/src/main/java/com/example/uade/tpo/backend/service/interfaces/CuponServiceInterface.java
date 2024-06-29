package com.example.uade.tpo.backend.service.interfaces;

public interface CuponServiceInterface {

    public String validarCupon(String cupon);
    public void createCupon(String name, Long descuento);
}