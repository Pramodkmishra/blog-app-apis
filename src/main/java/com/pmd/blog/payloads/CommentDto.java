package com.pmd.blog.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	private Integer id;
	private String comment;
	private PostDto post;
	private UserDto user;
}
