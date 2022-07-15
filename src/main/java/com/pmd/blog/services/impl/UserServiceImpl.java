package com.pmd.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmd.blog.entities.User;
import com.pmd.blog.exceptions.ResourceNotFoundException;
import com.pmd.blog.payloads.UserDto;
import com.pmd.blog.repositories.UserRepo;
import com.pmd.blog.services.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUSer(UserDto userDto) {
		// TODO Auto-generated method stub
		User user=this.dtoToUser(userDto);
		User savedUser=userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("USer"," id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		User savedUser=userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("USer"," id",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users =userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}
	
	@Override
	public void deleteUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("USer"," id",userId));
		userRepo.deleteById(userId);
		
	}
	
	private User dtoToUser(UserDto userDto) {
		User user=modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
	}
	private UserDto userToDto(User user) {
		UserDto userDto=modelMapper.map(user,UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return userDto;
	}

	

}
