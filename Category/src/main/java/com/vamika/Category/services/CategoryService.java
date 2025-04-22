package com.vamika.Category.services;

import java.util.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vamika.Category.dto.CategoryDto;
import com.vamika.Category.dto.CategoryTypeDto;
import com.vamika.Category.entities.Category;
import com.vamika.Category.entities.CategoryType;
import com.vamika.Category.exceptions.ResourceNotFoundEx;
import com.vamika.Category.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryRepository getCategoryRepository() {
		return categoryRepository;
	}

	public void setCategoryRepository(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public Category getCategory(UUID categoryId) {
		 Optional<Category> category = categoryRepository.findById(categoryId);
		 return category.orElseThrow(() -> new RuntimeException("Category not found"));
	}
	public Category createCategory(CategoryDto categoryDto) {
		Category category = mapToEntity(categoryDto);
		return categoryRepository.save(category);
	}

	private Category mapToEntity(CategoryDto categoryDto) {
		Category category = Category.builder()
				.name(categoryDto.getName())
				.code(categoryDto.getCode())
				.description(categoryDto.getDescription())
				.build();
		if (categoryDto.getCategoryTypeList() != null) {
			List<CategoryType> categoryTypes = mapToCategoryTypesList(categoryDto.getCategoryTypeList(), category);
			category.setCategoryTypes(categoryTypes);
		}
		return category;
	}

	private List<CategoryType> mapToCategoryTypesList(List<CategoryTypeDto> categoryTypeList, Category category) {
		
		return categoryTypeList.stream().map(categoryTypeDto -> {
			CategoryType categoryType = new CategoryType();
			categoryType.setName(categoryTypeDto.getName());
			categoryType.setCode(categoryTypeDto.getCode());
			categoryType.setDescription(categoryTypeDto.getDescription());
			categoryType.setCategory(category);
			return categoryType;
		}).collect(Collectors.toList());
	}
	
	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}
	
	public Category updateCategory(UUID categoryId, CategoryDto categoryDto) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundEx("Category not found with id: " + categoryDto.getId()));
		
		if (categoryDto.getName() != null) {
			category.setName(categoryDto.getName());
		}
		if (categoryDto.getCode() != null) {
			category.setCode(categoryDto.getCode());
		}
		if (categoryDto.getDescription() != null) {
			category.setDescription(categoryDto.getDescription());
		}
		
		List<CategoryType> existing = category.getCategoryTypes();
		List<CategoryType> list= new ArrayList<>();
		
		if (categoryDto.getCategoryTypeList() != null) {
			categoryDto.getCategoryTypeList().forEach(categoryTypeDto -> {
				if (categoryTypeDto.getId() != null) {
					Optional<CategoryType> categoryType = existing.stream()
							.filter(ct -> ct.getId().equals(categoryTypeDto.getId()))
							.findFirst();
					if (categoryType.isPresent()) {
						CategoryType categoryType1 = categoryType.get();
						categoryType1.setName(categoryTypeDto.getName());
						categoryType1.setCode(categoryTypeDto.getCode());
						categoryType1.setDescription(categoryTypeDto.getDescription());
						list.add(categoryType1);
					}
				} 
				else {
						
				CategoryType categoryType = new CategoryType();
				categoryType.setName(categoryTypeDto.getName());
				categoryType.setCode(categoryTypeDto.getCode());
				categoryType.setDescription(categoryTypeDto.getDescription());
				categoryType.setCategory(category);
				list.add(categoryType);
			}
			});
		}
		
		return categoryRepository.save(category);
	}
	
	public void deleteCategory(UUID categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundEx("Category not found with id: " + categoryId));
		categoryRepository.delete(category);
	}
}
