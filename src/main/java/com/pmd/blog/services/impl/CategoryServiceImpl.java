package com.pmd.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmd.blog.entities.Category;
import com.pmd.blog.exceptions.ResourceNotFoundException;
import com.pmd.blog.payloads.CategoryDto;
import com.pmd.blog.repositories.CategoryRepo;
import com.pmd.blog.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category = categoryDtoToCategory(categoryDto);
		Category savedCategory=categoryRepo.save(category);
		return categoryToCategoryDto(savedCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category"," Id",categoryId));
		category.setCategoryName(categoryDto.getCategoryName());
		category.setCategoryDesc(categoryDto.getCategoryDesc());
		Category updatedCategory=categoryRepo.save(category);
		return categoryToCategoryDto(updatedCategory);

	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category"," Id",categoryId));
		categoryRepo.deleteById(categoryId);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category"," Id",categoryId));
		return categoryToCategoryDto(category);
	}

	@Override
	public List<CategoryDto> getCategories() {
		// TODO Auto-generated method stub
		List<Category> categories = categoryRepo.findAll();
		return categories.stream().map(cat->categoryToCategoryDto(cat)).collect(Collectors.toList());
	}
	private CategoryDto categoryToCategoryDto(Category category) {
		return modelMapper.map(category, CategoryDto.class);
	}
	private Category categoryDtoToCategory(CategoryDto categoryDto) {
		return modelMapper.map(categoryDto, Category.class);
	}
}
