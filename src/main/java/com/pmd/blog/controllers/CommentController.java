package com.pmd.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmd.blog.payloads.ApiResponse;
import com.pmd.blog.payloads.CommentDto;
import com.pmd.blog.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@PostMapping("/user/{userId}/post/{postId}")
	public ResponseEntity<CommentDto> postComment(@RequestBody CommentDto commentDto,
			@PathVariable("userId") Integer userId, @PathVariable("postId") Integer postId) {
		CommentDto postedCommentDto = commentService.postComment(commentDto, userId, postId);
		return new ResponseEntity<>(postedCommentDto, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId")Integer commentId){
		commentService.deletComment(commentId);
		return new ResponseEntity<>(new ApiResponse("comment deleted Successfully",true),HttpStatus.OK);
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<List<CommentDto>>getAllCommentsOnPost(@PathVariable("postId")Integer postId){
		List<CommentDto> commentDtos = commentService.getAllCommentsOnPost(postId);
		return new ResponseEntity<List<CommentDto>>(commentDtos,HttpStatus.OK);
	}
	
}
