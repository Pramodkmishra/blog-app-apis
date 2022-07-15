package com.pmd.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	@NotEmpty(message = "Name can not be empty")
	@Size(min =3,max=30,message="Size of name must be greater than 2 and less than 30")
	private String name;
	@Email(message = "Email is not valid")
	@NotEmpty(message="Email can not be empty")
	private String email;
	@NotBlank(message = "Abount can not be blank")
	private String about;
	@NotEmpty(message="Password can not be empty")
	private String password;
	//private Set<CommentDto> comments = new HashSet<>();
}
