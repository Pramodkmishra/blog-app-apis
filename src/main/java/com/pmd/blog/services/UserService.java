package com.pmd.blog.services;

import java.util.List;

import com.pmd.blog.payloads.UserDto;

public interface UserService {
	UserDto createUSer(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);

}
