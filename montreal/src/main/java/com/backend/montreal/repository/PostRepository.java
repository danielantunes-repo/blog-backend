package com.backend.montreal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.montreal.auth.Usuario;
import com.backend.montreal.entity.Post;
import com.backend.montreal.entity.Tema;

public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findByUsuario(Usuario usuario); // Buscar posts por usuário
    List<Post> findByTema(Tema tema); // Buscar posts por tema
    Optional<Post>  findByTituloAndUsuarioAndTema(String titulo, Usuario usuario, Tema tema);

}
