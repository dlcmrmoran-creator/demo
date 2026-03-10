package com.ejemplo.demo.controller;

import com.ejemplo.demo.entity.Persona;
import com.ejemplo.demo.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    // ========== GET ALL ==========
    @GetMapping
    public List<Persona> obtenerTodas() {
        return personaRepository.findAll();
    }

    // ========== GET BY ID ==========
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Persona> persona = personaRepository.findById(id);
        if (persona.isPresent()) {
            return ResponseEntity.ok(persona.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontró persona con ID: " + id);
    }

    // ========== POST ==========
    @PostMapping
    public ResponseEntity<?> crearPersona(@RequestBody Persona persona) {
        try {
            // Validar email único
            if (personaRepository.existsByEmail(persona.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("El email ya existe");
            }

            persona.setFechaCreacion(LocalDateTime.now());
            Persona nueva = personaRepository.save(persona);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear: " + e.getMessage());
        }
    }

    // ========== PUT ==========
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPersona(@PathVariable Long id, @RequestBody Persona persona) {
        try {
            Optional<Persona> personaOpt = personaRepository.findById(id);
            if (!personaOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró persona con ID: " + id);
            }

            Persona existente = personaOpt.get();

            // Actualizar solo campos que vienen en la petición
            if (persona.getNombre() != null && !persona.getNombre().trim().isEmpty()) {
                existente.setNombre(persona.getNombre());
            }

            if (persona.getEmail() != null && !persona.getEmail().trim().isEmpty()) {
                // Verificar que el email no esté usado por otra persona
                Optional<Persona> emailExistente = personaRepository.findByEmail(persona.getEmail());
                if (emailExistente.isPresent() && !emailExistente.get().getId().equals(id)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("El email ya está en uso por otra persona");
                }
                existente.setEmail(persona.getEmail());
            }

            if (persona.getEdad() != null) {
                existente.setEdad(persona.getEdad());
            }

            Persona actualizada = personaRepository.save(existente);
            return ResponseEntity.ok(actualizada);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar: " + e.getMessage());
        }
    }

    // ========== DELETE ==========
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPersona(@PathVariable Long id) {
        try {
            if (!personaRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró persona con ID: " + id);
            }

            personaRepository.deleteById(id);
            return ResponseEntity.ok("Persona eliminada correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar: " + e.getMessage());
        }
    }
}