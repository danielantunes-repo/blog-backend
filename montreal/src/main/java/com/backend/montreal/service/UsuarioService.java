package com.backend.montreal.service;

import java.util.List;
import com.backend.montreal.auth.Usuario;
import com.backend.montreal.auth.UsuarioDTO;

public interface UsuarioService {
	Usuario register(UsuarioDTO usuarioDTO);
//    Usuario saveUsuario(Usuario usuario);
    List<Usuario> getAllUsuarios();
    Usuario getUsuarioById(Long id);
    Usuario updateUsuario(Long id, Usuario updatedUsuario);
    void deleteUsuario(Long id);
}
