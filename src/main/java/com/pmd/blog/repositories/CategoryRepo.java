package com.pmd.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmd.blog.entities.Category;
import com.pmd.blog.payloads.CategoryDto;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
}
