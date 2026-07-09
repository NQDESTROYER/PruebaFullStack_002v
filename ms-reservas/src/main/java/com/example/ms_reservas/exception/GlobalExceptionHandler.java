package com.example.ms_reservas.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Maneja excepciones de recursos no encontrados (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
        log.error("Error 404: {}", ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("error", "Recurso No Encontrado");
        response.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Maneja violaciones a reglas de negocio (ej. fechas inválidas) (400)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Error 400: Violación de regla de negocio - {}", ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("error", "Solicitud Incorrecta");
        response.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Maneja validaciones de los DTOs (@NotNull, @Future, etc.) (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidations(MethodArgumentNotValidException ex) {
        log.warn("Error 400: Validaciones fallidas en la petición");
        Map<String, String> errores = new HashMap<>();
        errores.put("error", "Validación Fallida"); // Agregamos una clave genérica
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje); // Detalle por cada campo fallido
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    // Atrapalotodo para errores no controlados (500) - Evita exponer la traza del stack al cliente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        log.error("Error 500: Error interno del servidor", ex);
        Map<String, String> response = new HashMap<>();
        response.put("error", "Error Interno");
        response.put("mensaje", "Ocurrió un error inesperado. Por favor, contacte al administrador.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}