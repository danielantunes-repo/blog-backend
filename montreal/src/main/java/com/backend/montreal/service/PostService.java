package com.backend.montreal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.montreal.entity.Post;

@Service
public interface PostService {


//	Post savePost(Post post); 
//	Post criarPost(Long usuarioId, Long temaId, Post post);
	Post criarPost(Post post, Long usuarioId, Long temaId);
	
	List<Post> getAllPosts();
	
	Post getPostById(Long postId);
	
	Post editPost(Long postId, Post updatedPost);
	
	void deletePost(Long postId);
	

}
