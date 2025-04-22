package com.vamika.Product.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vamika.Product.dto.ProductDto;
import com.vamika.Product.entities.Product;
import com.vamika.Product.services.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	public ProductController(ProductService productService) {
		this.setProductService(productService);
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(required = false) UUID categoryId, @RequestParam(required = false) UUID typeId, @RequestParam(required = false) String slug) {
		List<ProductDto> productList = new ArrayList<>();
		if (StringUtils.isNotBlank(slug)) {
			ProductDto productDto = productService.getProductBySlug(slug);
			productList.add(productDto);
		} else {
			productList = productService.getAllProducts(categoryId, typeId);
		}
		return new ResponseEntity<>(productList, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable UUID id) {
		ProductDto productDto = productService.getProductById(id);
		return new ResponseEntity<>(productDto, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
		Product product = productService.addProduct(productDto);
		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody ProductDto productDto, @PathVariable UUID id) {
		Product product = productService.updateProduct(productDto, id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
}
