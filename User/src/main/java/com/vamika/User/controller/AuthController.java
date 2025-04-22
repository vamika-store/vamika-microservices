package com.vamika.User.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.vamika.User.config.JWTTokenHelper;
import com.vamika.User.dto.LoginRequest;
import com.vamika.User.dto.RegistrationResponse;
import com.vamika.User.dto.RegistrationRequest;
import com.vamika.User.dto.UserToken;
import com.vamika.User.entities.User;
import com.vamika.User.services.RegistrationService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	RegistrationService registrationService;
	
	@Autowired
	UserDetailsService userDetailService;
	
	@Autowired
	JWTTokenHelper jwtTokenHelper;
	
	@PostMapping("/login")
	public ResponseEntity<UserToken> login(@RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUserName(), loginRequest.getPassword());
			Authentication authenticationResponse = this.authenticationManager.authenticate(authentication);
			
			if (authenticationResponse.isAuthenticated()) {
				User user = (User) authenticationResponse.getPrincipal();
				if (!user.isEnabled()) {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
				String token = jwtTokenHelper.generateToken(user.getEmail());
				UserToken userToken = UserToken.builder().token(token).build();
				return new ResponseEntity<>(userToken, HttpStatus.OK);
			}
		} catch (BadCredentialsException e) {
			System.out.println("Invalid credentials: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			System.out.println("Error during login: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/register")
	public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
		RegistrationResponse registrationResponse = registrationService.createUser(request);
		
		return new ResponseEntity<>(registrationResponse,
				registrationResponse.getCode() == 200 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verifyCode(@RequestBody Map<String, String>map) {
		String userName = map.get("userName");
		String code = map.get("code");
		
		User user = (User) userDetailService.loadUserByUsername(userName);
		if (user != null && user.getVerificationCode().equals(code)) {
			registrationService.verifyUser(userName);
			return new ResponseEntity<>("User verified successfully", HttpStatus.OK);
		}
	  return new ResponseEntity<>("Invalid verification code", HttpStatus.BAD_REQUEST);
	}
}
