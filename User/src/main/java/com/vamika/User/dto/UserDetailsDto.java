package com.vamika.User.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
	
	private UUID id;
	private String firstName;
	private String lastName ;
	private String email;
	private String phoneNumber;	
	private Object authorityList;
}
