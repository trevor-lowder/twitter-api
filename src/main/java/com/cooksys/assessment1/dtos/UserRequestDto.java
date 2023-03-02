package com.cooksys.assessment1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The UserRequestDto class creates an object used to represent the form of the
 * data received from a client.
 * 
 * @author Scott VanBrunt
 *
 */
@NoArgsConstructor
@Data
public class UserRequestDto {

	private CredentialsDto credentials;

	private ProfileDto profile;
}
