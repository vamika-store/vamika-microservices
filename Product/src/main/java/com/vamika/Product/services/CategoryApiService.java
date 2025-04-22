package com.vamika.Product.services;

import com.vamika.Category.entities.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CategoryApiService {
	
	@Autowired
	private RestTemplate restTemplate;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Value("${category.service.url}")
	private String categoryServiceUrl;
	
	public Category getCategoryById(String categoryId) {
		String url = String.format("%s/api/category/%s", categoryServiceUrl, categoryId);
		try {
			Category category = restTemplate.getForObject(url, Category.class);
			return category;
		} catch (Exception e) {
			System.err.println("Error fetching category: " + e.getMessage());
			return null;
		}
	}
}
