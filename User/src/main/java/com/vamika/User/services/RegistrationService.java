package com.vamika.User.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vamika.User.dto.RegistrationRequest;
import com.vamika.User.dto.RegistrationResponse;
import com.vamika.User.entities.User;
import com.vamika.User.helper.VerificationCodeGenerator;
import com.vamika.User.repositories.UserDetailRepository;

@Service
public class RegistrationService {
	
	@Autowired
	private UserDetailRepository userDetailRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private EmailService emailService;
	
	public RegistrationResponse createUser(RegistrationRequest request) {
		
		User existingUser = userDetailRepository.findByEmail(request.getEmail());
		
		if (existingUser != null) {
			return RegistrationResponse.builder()
					.code(400)
					.message("Email already exists")
					.build();
		}
		
		try {
			User user = new User();
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setEmail(request.getEmail());
			user.setEnabled(false);
			user.setPassword(passwordEncoder.encode(request.getPassword()));
			user.setPhoneNumber(request.getPhoneNumber());
			user.setProvider("manual");
			user.setCreatedOn(System.currentTimeMillis());
			user.setUpdatedOn(System.currentTimeMillis());
			
			String code = VerificationCodeGenerator.generateCode();
			
			user.setVerificationCode(code);
			user.setAuthorities(authorityService.getUserAuthority());
			userDetailRepository.save(user);
			emailService.sendMail(user);
			// Send verification email
			
			return RegistrationResponse.builder()
					.code(200)
					.message("User registered successfully")
					.build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Error creating user", e);
		}
	}
	
	public void verifyUser(String userName) {
		User user = userDetailRepository.findByEmail(userName);
		user.setEnabled(true);
		userDetailRepository.save(user);
	}
}
