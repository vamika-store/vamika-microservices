package com.vamika.Product.entities;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vamika.Category.entities.Category;
import com.vamika.Category.entities.CategoryType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@Column(nullable = false)
	private String name;
	
	@Column
	private String description;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	@Column(nullable = false)
	private String brand;
	
	@Column
	private Float rating;
	
	@Column(nullable = false)
	private Boolean isNewArrival;
	
	@Column(nullable = false, unique = true)
	private String slug;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date createdAt;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date updatedAt;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProductVariant> ProductVariants;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	@JsonIgnore
	private Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_type_id", nullable = false)
	@JsonIgnore
	private CategoryType categoryType;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Resources> resources;
	
	@PrePersist
	protected void onCreate() {
		createdAt = new java.util.Date();
		updatedAt = createdAt;
	}
	
	@PreUpdate
	protected void onUpdate() {
		updatedAt = new java.util.Date();
	}
	

}
