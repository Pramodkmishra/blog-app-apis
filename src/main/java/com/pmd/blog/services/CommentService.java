package com.pmd.blog.services;

import java.util.List;

import com.pmd.blog.payloads.CommentDto;

public interface CommentService {
	CommentDto postComment(CommentDto commentDto,Integer userId,Integer postId);
	void deletComment(Integer commentId);
	CommentDto getComment(Integer commentId);
	List<CommentDto> getCommentsUserAndPostWise(Integer userId,Integer postId);
	List<CommentDto> getAllCommentsOnPost(Integer postId);
}
