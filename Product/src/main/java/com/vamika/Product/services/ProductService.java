package com.vamika.Product.services;

import java.util.List;
import java.util.UUID;

import com.vamika.Product.dto.ProductDto;
import com.vamika.Product.entities.Product;

public interface ProductService {
	
	public Product addProduct(ProductDto product);
	public List<ProductDto> getAllProducts(UUID categoryId, UUID typeId);
	
	ProductDto getProductBySlug(String slug);
	
	ProductDto getProductById(UUID id);
	
	Product updateProduct(ProductDto productDto, UUID id);
	
	Product fetchProductById(UUID id) throws Exception;

}
