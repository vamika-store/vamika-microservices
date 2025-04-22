package com.vamika.User.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vamika.User.entities.User;
import com.vamika.User.repositories.UserDetailRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserDetailRepository userDetailRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDetailRepository.findByEmail(username);
		if (user == null) {
				throw new UsernameNotFoundException("User not found with email: " + username);
		}
		return user;
	}

}
