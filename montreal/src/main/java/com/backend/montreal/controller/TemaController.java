package com.backend.montreal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.montreal.entity.Tema;
import com.backend.montreal.service.TemaService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/tema")
@CrossOrigin("*")
public class TemaController {

    @Autowired
    private TemaService temaService;

    @PostMapping("/save")
    public ResponseEntity<?> createTema(@RequestBody Tema tema) {
        try {
            Tema createdTema = temaService.saveTema(tema);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTema);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar tema");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Tema>> getAllTemas() {
        try {
            return ResponseEntity.ok(temaService.getAllTemas());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTemaById(@PathVariable Long id) {
        try {
            Tema tema = temaService.getTemaById(id);
            return ResponseEntity.ok(tema);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTema(@PathVariable Long id, @RequestBody Tema updatedTema) {
        try {
            Tema tema = temaService.updateTema(id, updatedTema);
            return ResponseEntity.ok(tema);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar tema");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTema(@PathVariable Long id) {
        try {
            temaService.deleteTema(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir tema");
        }
    }
}
