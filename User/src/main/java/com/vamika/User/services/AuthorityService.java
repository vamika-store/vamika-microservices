package com.vamika.User.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vamika.User.entities.Authority;
import com.vamika.User.repositories.AuthorityRepository;


@Service
public class AuthorityService {
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	public List<Authority> getUserAuthority() {
		List<Authority> authorities = new ArrayList<>();
		Authority authority = authorityRepository.findByRoleCode("USER");
		authorities.add(authority);
		return authorities;
	}
	
	public Authority createAuthority(String role, String description) {
		Authority authority = Authority.builder()
				.roleCode(role)
				.roleDescription(description)
				.build();
		return authorityRepository.save(authority);			
	}
}
