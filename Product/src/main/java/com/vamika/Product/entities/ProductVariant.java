package com.vamika.Product.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_variant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@Column(nullable = false)
	private String color;
	
	@Column(nullable = false)
	private String size;
	
	@Column(nullable = false)
	private Integer stockQuantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	@JsonIgnore
	private Product product;
}
