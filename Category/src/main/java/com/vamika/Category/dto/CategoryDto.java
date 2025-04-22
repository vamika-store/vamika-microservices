package com.vamika.Category.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
	
	private UUID id;
	private String name;
	private String code;
	private String description;
	private List<CategoryTypeDto> categoryTypeList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<CategoryTypeDto> getCategoryTypeList() {
		return categoryTypeList;
	}

	public void setCategoryTypeList(List<CategoryTypeDto> categoryTypeList) {
		this.categoryTypeList = categoryTypeList;
	}
	

}
