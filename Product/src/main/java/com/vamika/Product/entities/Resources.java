package com.vamika.Product.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_resources")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resources {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String url;
	
	@Column(nullable = false)
	private String type;
	
	@Column(nullable = false)
	private Boolean isPrimary;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	@JsonIgnore
	private Product product;
}
