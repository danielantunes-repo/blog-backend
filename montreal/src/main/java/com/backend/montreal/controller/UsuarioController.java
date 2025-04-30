package com.backend.montreal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.montreal.dto.UsuarioDTO;
import com.backend.montreal.entity.Usuario;
import com.backend.montreal.repository.UsuarioRepository;
import com.backend.montreal.service.UsuarioService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UsuarioDTO usuarioDTO) {
		try {
			Usuario createdUsuario = usuarioService.register(usuarioDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar usuário");
		}
	}

	@GetMapping("/usuario/todos")
	public ResponseEntity<List<Usuario>> getAllUsuarios() {
		try {
			return ResponseEntity.ok(usuarioService.getAllUsuarios());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
		try {
			Usuario usuario = usuarioService.getUsuarioById(id);
			return ResponseEntity.ok(usuario);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PutMapping("/usuario/{id}")
	public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody Usuario updatedUsuario) {
		try {
			Usuario usuario = usuarioService.updateUsuario(id, updatedUsuario);
			return ResponseEntity.ok(usuario);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar usuário");
		}
	}

	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
		try {
			usuarioService.deleteUsuario(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir usuário");
		}
	}

	@GetMapping("/findByUsername/{username}")
	public ResponseEntity<UsuarioDTO> findUsuarioByUsername(String username) {
		Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);

		if (optionalUsuario.isPresent()) {
			UsuarioDTO usuarioDTO = new UsuarioDTO(optionalUsuario.get());
			return ResponseEntity.ok(usuarioDTO);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
