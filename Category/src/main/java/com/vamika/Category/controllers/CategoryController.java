package com.vamika.Category.controllers;

import com.vamika.Category.dto.CategoryDto;
import com.vamika.Category.entities.Category;
import com.vamika.Category.services.CategoryService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable(value= "id", required = true) UUID categoryId) {
		Category category = categoryService.getCategory(categoryId);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	@GetMapping	
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categorieList = categoryService.getAllCategory();
		return new ResponseEntity<>(categorieList, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
		Category category = categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(category, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable(value = "id", required = true) UUID categoryId) {
		Category updatedCategory = categoryService.updateCategory(categoryId, categoryDto);
		return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
	}
}
