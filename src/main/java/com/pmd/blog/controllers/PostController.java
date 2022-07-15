package com.pmd.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pmd.blog.payloads.ApiResponse;
import com.pmd.blog.payloads.PostDto;
import com.pmd.blog.payloads.PostResponse;
import com.pmd.blog.services.FileService;
import com.pmd.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId") Integer userId,
			@PathVariable("categoryId") Integer categoryId) {
		PostDto savedPost = postService.createPost(postDto, userId, categoryId);
		// return savedPost;
		return new ResponseEntity<PostDto>(savedPost, HttpStatus.CREATED);
	}

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getgetAllPostsByUser(@PathVariable("userId") Integer userId) {
		List<PostDto> posts = postService.getAllPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getgetAllPostsByCategory(@PathVariable("categoryId") Integer userId) {
		List<PostDto> posts = postService.getAllPostsByCategory(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	@GetMapping("/posts")
	public ResponseEntity<List<PostDto>> getPosts() {
		List<PostDto> postDtos = postService.getPosts();
		return new ResponseEntity<>(postDtos, HttpStatus.OK);
	}

	@GetMapping("/posts/page")
	public ResponseEntity<PostResponse> getAllPostsPageWise(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,//pageNumber starts form 0
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PostResponse postResponse = postService.getAllPostsPageWise(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);

	}

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable("postId") Integer postId) {
		PostDto postDto = postService.getPost(postId);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}

	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId) {
		postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted SuccessFully", true), HttpStatus.OK);
	}

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") Integer postId) {
		PostDto updatedPost = postService.updatePost(postDto, postId);
		return new ResponseEntity<>(updatedPost, HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keyword){
		List<PostDto> postDtos =postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	@PostMapping("/post/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam(value="image") MultipartFile image,@PathVariable("postId") Integer postId) throws IOException{
		PostDto post = postService.getPost(postId);
		String fileName = fileService.uploadImage(path, image);
		post.setImageName(fileName);
		postService.updatePost(post, postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
	}
	
	@GetMapping("/post/image/{imageName}")
	public void downlaodIamge(@PathVariable("imageName") String imageName,HttpServletResponse response)throws IOException{
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}
}


