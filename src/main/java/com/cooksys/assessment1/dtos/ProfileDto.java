package com.cooksys.assessment1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The ProfileDto class creates an object used to send only the desired data from a User.
 * 
 * @author Scott VanBrunt
 *
 */
@NoArgsConstructor
@Data
public class ProfileDto {

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phone;
}
