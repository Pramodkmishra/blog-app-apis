package com.pmd.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmd.blog.entities.Comment;
import com.pmd.blog.entities.Post;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
	
	List<Comment> findAllByPost(Post post);

}
