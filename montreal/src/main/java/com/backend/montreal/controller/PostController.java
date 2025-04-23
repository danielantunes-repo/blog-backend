package com.backend.montreal.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.montreal.entity.Post;
import com.backend.montreal.service.PostService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/post")
@CrossOrigin("*")
public class PostController {

	@Autowired
	private PostService postService;
	
	@PostMapping("/save")
	public ResponseEntity<?> createPost(@RequestBody Post post, @RequestParam Long usuarioId, @RequestParam Long temaId) {
		try {
			Post createPost = postService.criarPost(post, usuarioId, temaId);
			return ResponseEntity.status(HttpStatus.CREATED).body(createPost);
			
		} catch (Exception e ) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
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
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<?> getPostById(@PathVariable Long postId) {
		try {
			Post post = postService.getPostById(postId);
			return ResponseEntity.ok(post);
		} catch(EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	
}
