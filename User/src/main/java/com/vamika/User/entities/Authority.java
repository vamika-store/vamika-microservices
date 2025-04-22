package com.vamika.User.entities;


import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AUTH_AUTHORITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority implements GrantedAuthority {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@Column(nullable = false)
	private String roleCode;
	
	@Column(nullable = false)
	private String roleDescription;
	
	@Override
	public String getAuthority() {
		return roleCode;
	}
}
