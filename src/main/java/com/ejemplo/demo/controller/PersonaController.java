package com.ejemplo.demo.controller;

import com.ejemplo.demo.entity.Persona;
import com.ejemplo.demo.repository.PersonaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    private final PersonaRepository personaRepository;

    public PersonaController(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @GetMapping
    public List<Persona> listar() {
        return personaRepository.findAll();
    }

    @PostMapping
    public Persona crear(@RequestBody Persona persona) {
        return personaRepository.save(persona);
    }
}