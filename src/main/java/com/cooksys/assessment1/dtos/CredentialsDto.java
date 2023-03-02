package com.cooksys.assessment1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The CredentialsDto class creates an object used to contain only the desired data from a User.
 * 
 * @author Scott VanBrunt
 *
 */
@NoArgsConstructor
@Data
public class CredentialsDto {

	private String username;
	
	private String password;
}
