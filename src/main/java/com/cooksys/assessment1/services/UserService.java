package com.cooksys.assessment1.services;

import java.util.List;

import com.cooksys.assessment1.dtos.CredentialsDto;
import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;

/**
 * The UserService interface is an intermediate interface used to allow simple update/replacement of
 * the implementing class.
 * 
 * @author Scott VanBrunt
 *
 */
public interface UserService {

	/**
	 * 
	 * @return all not deleted Users in a List of UserResponseDtos.
	 */
	List<UserResponseDto> getAllUsers();

	/**
	 * 
	 * @param userRequestDto containing a CredentialsDto and ProfileDto.
	 * @return userResponseDto containing only data to send to client.
	 */
	UserResponseDto createUser(UserRequestDto userRequestDto);

	/**
	 * 
	 * @param userName String from the request URL to search for
	 * @return userResponseDto containing only data to send to client
	 */
	UserResponseDto getUser(String userName);

	/**
	 * Takes in a userRequestDto to verify credentials and change username.
	 * 
	 * @param userName String to change selected username to.
	 * @return userResponseDto containing only data to send to client
	 */
	UserResponseDto renameUser(UserRequestDto userRequestDto, String userName);

	/**
	 * Verifies user and if verified deletes username passed in URL.
	 * 
	 * @param credentialsDto to verify user
	 * @param userName of user to delete
	 * @return userResponseDto containing only data to send to client
	 */
	UserResponseDto deleteUser(CredentialsDto credentialsDto, String userName);

}
