package com.backend.montreal.service;

import java.util.List;
import java.util.Optional;

import com.backend.montreal.dto.UsuarioDTO;
import com.backend.montreal.entity.Usuario;

public interface UsuarioService {
	Usuario register(UsuarioDTO usuarioDTO);

	List<Usuario> getAllUsuarios();

	Usuario getUsuarioById(Long id);

	Usuario updateUsuario(Long id, Usuario updatedUsuario);

	void deleteUsuario(Long id);

	Optional<UsuarioDTO> findUsuarioByUsername(String username);
}
