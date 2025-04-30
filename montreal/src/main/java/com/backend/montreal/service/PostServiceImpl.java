package com.backend.montreal.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.backend.montreal.entity.Post;
import com.backend.montreal.entity.Usuario;
import com.backend.montreal.repository.PostRepository;
import com.backend.montreal.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Long getTotalPosts() {
		return postRepository.getTotalPosts();
	}

	@Override
	public List<Map<String, Object>> getPostsCountByUser() {
		try {
			List<Object[]> results = postRepository.countPostsByUser();
			List<Map<String, Object>> response = new ArrayList<>();

			for (Object[] result : results) {
				Map<String, Object> postCount = new HashMap<>();
				postCount.put("usuario_id", result[0]);
				postCount.put("post_count", result[1]);
				postCount.put("name", result[2]);
				postCount.put("username", result[3]);
				response.add(postCount);
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public Post createPost(Post post, String username) {

		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

		post.setAutor(usuario);

		post.setDate(new Date());

		return postRepository.save(post);
	}

	public List<Post> getAllPosts() {
		List<Post> posts = postRepository.findAll();
		System.out.println("Posts retornados: " + posts);
		return posts;
	}

	public Post getPostById(Long postId) {
		Optional<Post> optionalpost = postRepository.findById(postId);
		if (optionalpost.isPresent()) {
			Post post = optionalpost.get();
			return postRepository.save(post);
		} else {
			throw new EntityNotFoundException("Post não encontrado");
		}
	}

	public void deletePost(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new EntityNotFoundException("Post não encontrado para exclusão"));

		Usuario usuarioAtual = getUsuarioAutenticado();
		if (!post.getAutor().getUsername().equals(usuarioAtual.getUsername())) {
			throw new SecurityException("Você não tem permissão para excluir este post");
		}

		postRepository.deleteById(postId);
	}

	public Post editPost(Long postId, Post updatedPost) {
		Post existingPost = postRepository.findById(postId)
				.orElseThrow(() -> new EntityNotFoundException("Post não encontrado para edição"));

		Usuario usuarioAtual = getUsuarioAutenticado();
		if (!existingPost.getAutor().getUsername().equals(usuarioAtual.getUsername())) {
			throw new SecurityException("Você não tem permissão para editar este post");
		}

		existingPost.setTitulo(updatedPost.getTitulo());
		existingPost.setTexto(updatedPost.getTexto());
		existingPost.setDate(new Date());

		return postRepository.save(existingPost);
	}

	private Usuario getUsuarioAutenticado() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		return usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));
	}

}
