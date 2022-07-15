package com.pmd.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pmd.blog.entities.Category;
import com.pmd.blog.entities.Post;
import com.pmd.blog.entities.User;
import com.pmd.blog.exceptions.ResourceNotFoundException;
import com.pmd.blog.payloads.PostDto;
import com.pmd.blog.payloads.PostResponse;
import com.pmd.blog.repositories.CategoryRepo;
import com.pmd.blog.repositories.PostRepo;
import com.pmd.blog.repositories.UserRepo;
import com.pmd.blog.services.PostService;
@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User" , "USer ID ", userId));
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category" , "Category ID ", userId));
		Post post = modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		Post savedPost = postRepo.save(post);
		return modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ", "Post Id", postId));
		postRepo.deleteById(postId); 
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post =postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ", "Post Id", postId));
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		postRepo.save(post);
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPosts() {
		List<Post> posts =postRepo.findAll();
		List<PostDto> postsDto= posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postsDto;
	}

	@Override
	public PostDto getPost(Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ","Post Id",postId));
		return this.modelMapper.map(post,PostDto.class);
	}

	@Override
	public List<PostDto> getAllPostsByUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ", "User Id", userId));
		List<Post> posts =postRepo.findByUser(user);
		List<PostDto> postsDto = posts.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postsDto;
	}

	@Override
	public List<PostDto> getAllPostsByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ", "Category Id", categoryId));
		List<Post> posts =postRepo.findByCategory(category);
		List<PostDto> postsDto = posts.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postsDto;
		
	}

	@Override
	public PostResponse getAllPostsPageWise(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		PostResponse postResponse = new PostResponse();
		Sort sort = sortDir.equalsIgnoreCase("Desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost =postRepo.findAll(pageable);
		List<Post> posts =pagePost.getContent();
		List<PostDto> postsDto= posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		postResponse.setContent(postsDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts=postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos= posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
