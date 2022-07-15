package com.pmd.blog.payloads;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	private Integer categoryId;
	@NotEmpty(message="Category name can not be empty")
	private String categoryName;
	@NotEmpty(message="Category description can not be empty")
	private String categoryDesc;

}
