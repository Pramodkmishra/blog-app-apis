package com.pmd.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmd.blog.entities.Comment;
import com.pmd.blog.entities.Post;
import com.pmd.blog.entities.User;
import com.pmd.blog.exceptions.ResourceNotFoundException;
import com.pmd.blog.payloads.CommentDto;
import com.pmd.blog.repositories.CommentRepo;
import com.pmd.blog.repositories.PostRepo;
import com.pmd.blog.repositories.UserRepo;
import com.pmd.blog.services.CommentService;
@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto postComment(CommentDto commentDto, Integer userId, Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post ", "post id ", postId));
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ", "user id ", userId));
		Comment comment =this.modelMapper.map(commentDto,Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment =commentRepo.save(comment);
		return this.modelMapper.map(savedComment,CommentDto.class);
	}

	@Override
	public void deletComment(Integer commentId) {
		Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment"," Comment Id ",commentId));
		commentRepo.deleteById(commentId);
		// TODO Auto-generated method stub

	}

	@Override
	public CommentDto getComment(Integer commentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentDto> getCommentsUserAndPostWise(Integer userId, Integer postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentDto> getAllCommentsOnPost(Integer postId) {
		Post post =  postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post ", "post id ", postId));
		List<Comment> comments =commentRepo.findAllByPost(post);
		List<CommentDto> commentDtos = comments.stream().map(comment -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
		return commentDtos;
	}

}
