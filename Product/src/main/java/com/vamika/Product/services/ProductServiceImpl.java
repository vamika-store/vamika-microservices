package com.vamika.Product.services;

import java.util.List;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vamika.Category.services.CategoryService;
import com.vamika.Product.dto.*;
import com.vamika.Product.entities.*;
import com.vamika.Product.mapper.ProductMapper;
import com.vamika.Product.specification.ProductSpecification;
import com.vamika.Product.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductMapper productMapper;
	
	public ProductRepository getProductRepository() {
		return productRepository;
	}

	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public Product addProduct(ProductDto productDto) {
		Product product =  productMapper.mapToProductEntity(productDto);
		return productRepository.save(product);
	}
	
	@Override
	public List<ProductDto> getAllProducts(UUID categoryId, UUID typeId) {
		
		Specification<Product> productSpecification = Specification.where(null);
		
		if (categoryId != null) {
			productSpecification = productSpecification.and(ProductSpecification.hasCategoryId(categoryId));
		}
		
		if (typeId != null) {
			productSpecification = productSpecification.and(ProductSpecification.hasCategoryTypeId(typeId));
		}
		
		List<Product> products = productRepository.findAll(productSpecification);
		return productMapper.getProductDtos(products);
	}
	
	@Override
	public ProductDto getProductBySlug(String slug) {
		Product product = productRepository.findBySlug(slug);
		if (null == product) {
			throw new RuntimeException("Product not found");
		}
		
		ProductDto productDto = productMapper.mapProductToDto(product);
		productDto.setCategoryId(product.getCategory().getId());
		productDto.setCategoryTypeId(product.getCategoryType().getId());
		productDto.setVariants(productMapper.mapProductVarientListToDto(product.getProductVariants()));
		productDto.setProductResources(productMapper.mapToProductResourceListToDto(product.getResources()));
		return productDto;
	}
	
	@Override
	public ProductDto getProductById(UUID id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
		ProductDto productDto = productMapper.mapProductToDto(product);
		productDto.setCategoryId(product.getCategory().getId());
		productDto.setCategoryTypeId(product.getCategoryType().getId());
		productDto.setVariants(productMapper.mapProductVarientListToDto(product.getProductVariants()));
		productDto.setProductResources(productMapper.mapToProductResourceListToDto(product.getResources()));
		return productDto;
	}
	
	@Override
	public Product updateProduct(ProductDto productDto, UUID id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
		productDto.setId(product.getId());
		return productRepository.save(productMapper.mapToProductEntity(productDto));	
	}
	
	@Override
	public Product fetchProductById(UUID id) throws Exception {
		return productRepository.findById(id).orElseThrow(BadRequestException::new);
	}
}
