package com.backend.montreal.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.montreal.auth.Usuario;
import com.backend.montreal.entity.Post;
import com.backend.montreal.entity.Tema;
import com.backend.montreal.repository.PostRepository;
import com.backend.montreal.repository.TemaRepository;
import com.backend.montreal.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepository postRepository;
	
	 @Autowired
	 private TemaRepository temaRepository;
	    
	 @Autowired
	 private UsuarioRepository usuarioRepository;
	 
	 public Post criarPost(Post post, Long usuarioId, Long temaId) {
		    System.out.println("Criando post para usuarioId: " + usuarioId + ", temaId: " + temaId);
		    
		    Usuario usuario = usuarioRepository.findById(usuarioId)
		        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		    System.out.println("Usuário encontrado: " + usuario.getUsername());

		    Tema tema = temaRepository.findById(temaId)
		        .orElseThrow(() -> new RuntimeException("Tema não encontrado"));
		    System.out.println("Tema encontrado: " + tema.getDescricao());
		    
		    Optional<Post> existingPost = postRepository.findByTituloAndUsuarioAndTema(
		            post.getTitulo(), usuario, tema);

		        if (existingPost.isPresent()) {
		            throw new RuntimeException("Post com este título já existe para o usuário e tema selecionados");
		        }

		    post.setUsuario(usuario);
		    post.setTema(tema);
		    post.setDate(new Date());

		    return postRepository.save(post);
		}
	
	
	
	public List<Post> getAllPosts(){
		return postRepository.findAll();
	}
	
	public Post getPostById(Long postId) {
		Optional<Post> optionalpost = postRepository.findById(postId);
		if(optionalpost.isPresent()) {
			Post post =optionalpost.get();
			return postRepository.save(post);
		} else {
			throw new EntityNotFoundException("Post não encontrado");
		}
	}
	
	public void deletePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            postRepository.deleteById(postId);
        } else {
            throw new EntityNotFoundException("Post não encontrado para exclusão");
        }
    }
    
    public Post editPost(Long postId, Post updatedPost) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            existingPost.setTitulo(updatedPost.getTitulo());
            existingPost.setTexto(updatedPost.getTexto());
            existingPost.setDate(new Date()); 
            return postRepository.save(existingPost);
        } else {
            throw new EntityNotFoundException("Post não encontrado para edição");
        }
    }

}
