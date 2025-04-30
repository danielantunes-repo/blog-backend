package com.backend.montreal.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.montreal.entity.Post;
import com.backend.montreal.entity.Usuario;
import com.backend.montreal.repository.PostRepository;
import com.backend.montreal.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;  

    @Mock
    private PostRepository postRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PostServiceImpl postServiceImpl;  

    @Test
    void deveCriarPostQuandoUsuarioExiste() {
        Usuario usuario = new Usuario();
        usuario.setUsername("usuarioTeste");
        Post post = new Post();
        
        when(usuarioRepository.findByUsername("usuarioTeste")).thenReturn(Optional.of(usuario));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post result = postServiceImpl.createPost(post, "usuarioTeste");

        assertNotNull(result);
        verify(postRepository).save(post);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
        Post post = new Post();
        
        when(usuarioRepository.findByUsername("usuarioInexistente")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException
        		.class, () -> postServiceImpl.createPost(post, "usuarioInexistente"));
    }
}
