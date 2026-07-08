package com.example.ms_vehiculos.exception;
//esta clase define nuestro propio tipo de error eredando de runtimeexepcion
public class ResourceNotFoundException extends RuntimeException {
    //el constructor recibe el mensaje detallado
    public ResourceNotFoundException(String message){
        super(message);
    }
}
