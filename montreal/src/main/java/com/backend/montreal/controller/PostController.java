package com.backend.montreal.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

import com.backend.montreal.dto.PostRequest;
import com.backend.montreal.entity.Post;
import com.backend.montreal.entity.Usuario;
import com.backend.montreal.repository.PostRepository;
import com.backend.montreal.repository.UsuarioRepository;
import com.backend.montreal.service.PostService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/post")
@CrossOrigin("*")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PostRepository postRepository;

	@GetMapping("/todos")
	public ResponseEntity<Long> getTotalPosts() {
		Long totalPosts = postService.getTotalPosts();
		return ResponseEntity.ok(totalPosts);
	}

	@GetMapping("/count")
	public List<Object[]> countPostsByUser() {
		return postRepository.countPostsByUser();
	}

	@PostMapping("/create")
	public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedUsername = authentication.getName();

		Usuario usuario = usuarioRepository.findByUsername(loggedUsername)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

		Optional<Post> existingPost = postRepository.findByTituloAndAutor(postRequest.getTitulo(), usuario);

		if (existingPost.isPresent()) {
			Post existing = existingPost.get();
			return ResponseEntity.status(HttpStatus.CONFLICT).body(existing);
		}

		Post post = new Post();
		post.setTitulo(postRequest.getTitulo());
		post.setTexto(postRequest.getTexto());
		post.setAutor(usuario);
		post.setDate(new Date());
		post = postRepository.save(post);

		return ResponseEntity.status(HttpStatus.CREATED).body(post);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editPost(@PathVariable Long id, @RequestBody Post updatedPost) {
		try {
			Post editedPost = postService.editPost(id, updatedPost);
			return ResponseEntity.ok(editedPost);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao editar o post");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePost(@PathVariable Long id) {
		try {
			postService.deletePost(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o post");
		}
	}

	@GetMapping("/")
	public ResponseEntity<List<Post>> getAllPosts() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/{postId}")
	public ResponseEntity<?> getPostById(@PathVariable Long postId) {
		try {
			Post post = postService.getPostById(postId);
			return ResponseEntity.ok(post);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

}
