package com.backend.montreal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.montreal.entity.Post;
import com.backend.montreal.entity.Usuario;

public interface PostRepository extends JpaRepository<Post, Long> {
	Optional<Post> findByTituloAndAutor(String titulo, Usuario autor);

	@Query("SELECT COUNT(p) FROM Post p")
	Long getTotalPosts();

	@Query("SELECT u.id, u.name, u.username, COUNT(p) FROM Post p JOIN p.autor u GROUP BY u.id, u.name, u.username")
	List<Object[]> countPostsByUser();

}
