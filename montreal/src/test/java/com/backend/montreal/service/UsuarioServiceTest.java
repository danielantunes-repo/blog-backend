package com.backend.montreal.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.backend.montreal.dto.UsuarioDTO;
import com.backend.montreal.entity.Usuario;
import com.backend.montreal.repository.UsuarioRepository;

public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService; 

    @Mock
    private UsuarioRepository usuarioRepository; 

    @Mock
    private BCryptPasswordEncoder passwordEncoder; 

    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setName("João");
        usuarioDTO.setUsername("joao123");
        usuarioDTO.setPassword("senha123");
    }

    @Test
    void deveRegistrarUsuarioComSucesso() {
        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setName(usuarioDTO.getName());
        usuarioEsperado.setUsername(usuarioDTO.getUsername());
        usuarioEsperado.setPassword("senhaCodificada");
        usuarioEsperado.setRole("ROLE_USER");

        when(passwordEncoder.encode(usuarioDTO.getPassword())).thenReturn("senhaCodificada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioEsperado);

        Usuario usuarioRegistrado = usuarioService.register(usuarioDTO);

        assertNotNull(usuarioRegistrado);
        assertEquals("João", usuarioRegistrado.getName());
        assertEquals("joao123", usuarioRegistrado.getUsername());
        assertEquals("senhaCodificada", usuarioRegistrado.getPassword());
        assertEquals("ROLE_USER", usuarioRegistrado.getRole());

        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoSeNaoSalvarUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenThrow(new RuntimeException("Erro ao salvar usuário"));

        assertThrows(RuntimeException.class, () -> usuarioService.register(usuarioDTO));
    }
}
