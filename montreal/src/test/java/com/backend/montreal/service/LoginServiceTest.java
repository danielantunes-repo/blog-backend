package com.backend.montreal.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.backend.montreal.config.JwtServiceGenerator;
import com.backend.montreal.entity.Login;
import com.backend.montreal.entity.Usuario;
import com.backend.montreal.repository.LoginRepository;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;  

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private LoginRepository repository; 

    @Mock
    private JwtServiceGenerator jwtService;

    @Mock
    private Authentication authentication;

    private Login login;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        login = new Login();
        login.setUsername("joao123");
        login.setPassword("senha123");

        usuario = new Usuario();
        usuario.setUsername("joao123");
        usuario.setPassword("senhaCodificada");
    }

    @Test
    void deveRetornarTokenJwtQuandoUsuarioForAutenticadoComSucesso() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);

        when(repository.findByUsername("joao123"))
            .thenReturn(java.util.Optional.of(usuario));

        String jwtToken = "token-gerado";
        when(jwtService.generateToken(usuario)).thenReturn(jwtToken);

        String token = loginService.logar(login);
        assertNotNull(token);
        assertEquals("token-gerado", token);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(repository).findByUsername("joao123");
        verify(jwtService).generateToken(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
        when(repository.findByUsername("joao123"))
            .thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> loginService.logar(login));
    }

    @Test
    void deveLancarExcecaoQuandoFalharNaAutenticacao() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new RuntimeException("Falha na autenticação"));

        assertThrows(RuntimeException.class, () -> loginService.logar(login));
    }
}
