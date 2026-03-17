package com.ejemplo.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class EjemploController {//ejemplo de comentario

    @GetMapping("/info")
    public String info() {
        return "Endpoint de ejemplo funcionando correctamente desde la rama prueba!";
    }

    @GetMapping("/hora")
    public String horaActual() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "La hora actual es: " + ahora.format(formato);
    }

    @GetMapping("/saludo/{nombre}")
    public String saludoPersonalizado(@PathVariable String nombre) {
        return "Hola " + nombre + "! Bienvenido a mi API";
    }
}