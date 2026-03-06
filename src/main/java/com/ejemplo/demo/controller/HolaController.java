package com.ejemplo.demo.controller;

import com.ejemplo.demo.entity.Persona;
import com.ejemplo.demo.repository.PersonaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HolaController {

    private final PersonaRepository personaRepository;

    // 🔌 Inyección del repository
    public HolaController(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // 🔹 Endpoint de prueba
    @GetMapping("/hola")
    public String saludar() {
        return "Hola mundo";
    }

}