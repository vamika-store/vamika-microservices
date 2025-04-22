package com.vamika.Product.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vamika.Category.entities.Category;
import com.vamika.Category.entities.CategoryType;
import com.vamika.Category.services.CategoryService;
import com.vamika.Product.dto.ProductDto;
import com.vamika.Product.dto.ProductResourceDto;
import com.vamika.Product.dto.ProductVariantDto;
import com.vamika.Product.entities.Product;
import com.vamika.Product.entities.ProductVariant;
import com.vamika.Product.entities.Resources;

@Component
public class ProductMapper {
	
	@Autowired
	private CategoryService categoryService;

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	public Product mapToProductEntity(ProductDto productDto) {
		Product product = new Product();
		if (productDto.getId() != null) {
			product.setId(productDto.getId());
		}
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setBrand(productDto.getBrand());
		product.setIsNewArrival(productDto.getIsNewArrival());
		product.setPrice(productDto.getPrice());
		product.setRating(productDto.getRating());
		product.setSlug(productDto.getSlug());
		
		Category category = categoryService.getCategory(productDto.getCategoryId());
		if (category != null) {
			product.setCategory(category);
			UUID categoryTypeId = productDto.getCategoryTypeId();
			
			CategoryType categoryType = category.getCategoryTypes().stream()
					.filter(categoryType1 -> categoryType1.getId().equals(categoryTypeId))
					.findFirst()
					.orElse(null);
			product.setCategoryType(categoryType);
		}
		if (productDto.getVariants() != null) {
			product.setProductVariants(mapToProductVariant(productDto.getVariants(), product));
		}
		if (productDto.getProductResources() != null) {
			product.setResources(mapToProductResources(productDto.getProductResources(), product));
		}
				
		return product;
	}
	private List<Resources> mapToProductResources(List<ProductResourceDto> productResources, Product product) {
		return productResources.stream().map(productResourceDto -> {
			Resources resources = new Resources();
			if (productResourceDto.getId() != null) {
				resources.setId(productResourceDto.getId());
			}
			resources.setName(productResourceDto.getName());
			resources.setType(productResourceDto.getType());
			resources.setUrl(productResourceDto.getUrl());
			resources.setIsPrimary(productResourceDto.getIsPrimary());
			resources.setProduct(product);
			return resources;
		}).collect(Collectors.toList());
	}
	
	private List<ProductVariant> mapToProductVariant(List<ProductVariantDto> productVariantDtos, Product product) {
		return productVariantDtos.stream().map(productVariantDto -> {
			ProductVariant productVariant = new ProductVariant();
			if (productVariantDto.getId() != null) {
				productVariant.setId(productVariantDto.getId());
			}
			productVariant.setColor(productVariantDto.getColor());
			productVariant.setSize(productVariantDto.getSize());
			productVariant.setStockQuantity(productVariantDto.getStockQuantity());
			productVariant.setProduct(product);
			return productVariant;
		}).collect(Collectors.toList());
	}
	
	public List<ProductDto> getProductDtos(List<Product> products) {
		return products.stream().map(this::mapProductToDto).toList();
	}
	
	public ProductDto mapProductToDto(Product product) {
		return ProductDto.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.brand(product.getBrand())
				.isNewArrival(product.getIsNewArrival())
				.price(product.getPrice())
				.rating(product.getRating())
				.slug(product.getSlug())
				.thumbnail(getProductThumbnail(product.getResources()))
				.build();
	}
	
	private String getProductThumbnail(List<Resources> resources) {
		return resources.stream()
				.filter(resource -> resource.getType().equalsIgnoreCase("thumbnail"))
				.map(Resources::getUrl)
				.findFirst()
				.orElse(null);
	}
	
	public List<ProductVariantDto> mapProductVarientListToDto(List<ProductVariant> productVariants) {
		return productVariants.stream().map(this::mapProductVariantToDto).toList();
	}
	
	private ProductVariantDto mapProductVariantToDto(ProductVariant productVariant) {
		return ProductVariantDto.builder()
				.id(productVariant.getId())
				.color(productVariant.getColor())
				.size(productVariant.getSize())
				.stockQuantity(productVariant.getStockQuantity())
				.build();
	}
	
	public List<ProductResourceDto> mapToProductResourceListToDto(List<Resources> resources) {
		return resources.stream().map(this::mapToProductResourceDto).toList();
	}
	
	private ProductResourceDto mapToProductResourceDto(Resources resources) {
		return ProductResourceDto.builder()
				.id(resources.getId())
				.name(resources.getName())
				.type(resources.getType())
				.url(resources.getUrl())
				.isPrimary(resources.getIsPrimary())
				.build();
	}
}
