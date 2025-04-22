package com.vamika.Product.dto;

import java.math.BigDecimal;
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
public class ProductDto {
	
	private UUID id;
	private String name;
	private String description;
	private BigDecimal price;
	private String brand;
	private Boolean isNewArrival;
	private Float rating;
	private UUID categoryId;
	private String categoryName;
	private String thumbnail;
	private String slug;
	private UUID categoryTypeId;
	private String categoryTypeName;
	private List<ProductVariantDto> variants;
	private List<ProductResourceDto> ProductResources;
}
