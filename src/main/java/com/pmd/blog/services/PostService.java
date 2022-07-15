package com.pmd.blog.services;

import java.util.List;

import com.pmd.blog.payloads.PostDto;
import com.pmd.blog.payloads.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	void deletePost(Integer postId);
	PostDto updatePost(PostDto postDto,Integer postId);
	List<PostDto> getPosts();
	PostDto getPost(Integer postId);
	List<PostDto> getAllPostsByUser(Integer userId);
	List<PostDto> getAllPostsByCategory(Integer categoryId);
	PostResponse getAllPostsPageWise(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	List<PostDto> searchPost(String keyword);
	
}

