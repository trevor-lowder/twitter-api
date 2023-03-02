package com.cooksys.assessment1.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The UserRequestDto class creates an object used to send only the desired data
 * to a client.
 * 
 * @author Scott VanBrunt
 *
 */
@NoArgsConstructor
@Data
public class UserResponseDto {

	private String username;

	private ProfileDto profile;

	private Timestamp joined;
}
