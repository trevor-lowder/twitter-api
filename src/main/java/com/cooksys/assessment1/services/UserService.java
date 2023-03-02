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
	UserResponseDto updateUser(UserRequestDto userRequestDto, String userName);

	/**
	 * Verifies user and if verified deletes username passed in URL.
	 * 
	 * @param credentialsDto to verify user
	 * @param userName of user to delete
	 * @return userResponseDto containing only data to send to client
	 */
	UserResponseDto deleteUser(CredentialsDto credentialsDto, String userName);

	/**
	 * Takes in a string to find a User, then returns a list of all users following the selected user.
	 * 
	 * @param userName of the user to return from
	 * @return List of userReponseDtos of followers
	 */
	List<UserResponseDto> getFollowers(String userName);

	/**
	 * Takes in a string to find a User, then returns a list of all users followed by selected user.
	 * 
	 * @param userName of the user to return from
	 * @return List of userReponseDtos of following users
	 */
	List<UserResponseDto> getFollowing(String userName);

	/**
	 * Received credentials to access a User and adds the User linked to the provided
	 * username to their followed list.
	 * 
	 * @param credentialsDto to verify user
	 * @param userName to follow
	 */
	void follow(CredentialsDto credentialsDto, String userName);

	

}
