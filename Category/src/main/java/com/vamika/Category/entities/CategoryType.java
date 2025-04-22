package com.vamika.Category.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="category_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryType {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String code;
	
	@Column(nullable = false)
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	@JsonIgnore
	private Category category;

}
