package com.ejemplo.demo.repository;

import com.ejemplo.demo.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    
    // Verificar si existe un email (para POST)
    boolean existsByEmail(String email);
    
    // Buscar una persona por email (para PUT - validar que no esté duplicado)
    Optional<Persona> findByEmail(String email);
}