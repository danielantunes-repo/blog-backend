package com.backend.montreal.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.backend.montreal.entity.Post;

@Service
public interface PostService {

	Post createPost(Post post, String username);

	List<Post> getAllPosts();

	Post getPostById(Long postId);

	Post editPost(Long postId, Post updatedPost);

	void deletePost(Long postId);

	Long getTotalPosts();

	List<Map<String, Object>> getPostsCountByUser();

}
