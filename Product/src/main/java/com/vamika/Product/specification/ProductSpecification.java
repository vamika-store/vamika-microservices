package com.vamika.Product.specification;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.vamika.Product.entities.Product;

public class ProductSpecification {
	
	public static Specification<Product> hasCategoryId(UUID categoryId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("id"), categoryId);
	}
	
	public static Specification<Product> hasCategoryTypeId(UUID typeId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("categoryType").get("id"), typeId);
	}
}
