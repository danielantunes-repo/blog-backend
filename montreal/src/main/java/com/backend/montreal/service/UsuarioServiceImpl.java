package com.backend.montreal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.montreal.dto.UsuarioDTO;
import com.backend.montreal.entity.Usuario;
import com.backend.montreal.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Usuario register(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();
		usuario.setName(usuarioDTO.getName());
		usuario.setUsername(usuarioDTO.getUsername());
		usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
		usuario.setRole("ROLE_USER");
		return usuarioRepository.save(usuario);
	}

	@Override
	public List<Usuario> getAllUsuarios() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario getUsuarioById(Long id) {
		Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
		if (optionalUsuario.isPresent()) {
			return optionalUsuario.get();
		} else {
			throw new EntityNotFoundException("Usuário não encontrado");
		}
	}

	@Override
	public Usuario updateUsuario(Long id, Usuario updatedUsuario) {
		Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
		if (optionalUsuario.isPresent()) {
			Usuario existingUsuario = optionalUsuario.get();
			existingUsuario.setName(updatedUsuario.getName());
			existingUsuario.setUsername(updatedUsuario.getUsername());
			existingUsuario.setPassword(updatedUsuario.getPassword());
			return usuarioRepository.save(existingUsuario);
		} else {
			throw new EntityNotFoundException("Usuário não encontrado para edição");
		}
	}

	@Override
	public void deleteUsuario(Long id) {
		Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
		if (optionalUsuario.isPresent()) {
			usuarioRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("Usuário não encontrado para exclusão");
		}
	}

	@Override
	public Optional<UsuarioDTO> findUsuarioByUsername(String username) {
		Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);
		return optionalUsuario.map(UsuarioDTO::new);
	}

}
