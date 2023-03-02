package com.cooksys.assessment1.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.assessment1.dtos.CredentialsDto;
import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.Profile;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.exceptions.BadRequestException;
import com.cooksys.assessment1.exceptions.NotAuthorizedException;
import com.cooksys.assessment1.exceptions.NotFoundException;
import com.cooksys.assessment1.mappers.UserMapper;
import com.cooksys.assessment1.repositories.UserRepository;
import com.cooksys.assessment1.services.UserService;

import lombok.RequiredArgsConstructor;

/**
 * The UserServiceImpl class is the implementation of the UserService interface.
 * It handles method calls from the UserController to handle the logic of the
 * application and make calls further inside the application layers.
 * 
 * @author Scott VanBrunt
 *
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	/**
	 * 
	 * 
	 * @param userRequestDto
	 * @return User
	 */
	private User validateAndConvertUserInput(UserRequestDto userRequestDto) {

		User user = userMapper.requestDtoToEntity(userRequestDto);
			
		if(user.getCredentials() == null ||
				user.getCredentials().getUsername() == null ||
				user.getCredentials().getPassword() == null ||
				user.getProfile() == null ||
				user.getProfile().getEmail() == null){
			throw new BadRequestException("Username, Password, and Email are required fields.");
		}		
		
		return user;
	}
	
	private User findNotDeletedUser(String userName) {
		
		Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(userName);
		
		if(user.isEmpty()) {
			throw new NotFoundException("No user found by username '" + userName + "'");
		}
		
		return user.get();
	}
	
	/**
	 * 
	 * @return all not deleted Users in a List of UserResponseDtos.
	 */
	@Override
	public List<UserResponseDto> getAllUsers() {
		return userMapper.entitiesToDtos(userRepository.findByDeletedFalse());
	}

	/**
	 * Takes in a userRequestDto, sends it off for validation and conversion to a User,
	 * then searches the userRepository for a matching username. If match found tests deleted flag.
	 * If deleted and password matches updates deleted user with dto data and marked deleted false.
	 * If password doesn't match throws NotAuthorizedException.
	 * If no matching username is found creates a new user and pushes to the database.
	 * 
	 * @return a userResponseDto of an updated user or a newly added user.
	 */
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {

		User user = validateAndConvertUserInput(userRequestDto);
		
		// Check is username exists, if exists and not deleted throw exception, if exists and deleted toggle deleted flag and update other fields
		Optional<User> searchedUser = userRepository.findByCredentialsUsername(user.getCredentials().getUsername());
		if(!searchedUser.isEmpty()) {
			User foundUser = searchedUser.get();
			if(foundUser.getDeleted() == false) {
				throw new BadRequestException("The given username is already in use.");
			}
			else if(foundUser.getCredentials().getPassword().equals(user.getCredentials().getPassword())){
				foundUser.setDeleted(false);
				foundUser.getCredentials().setPassword(user.getCredentials().getPassword());;
				foundUser.getProfile().setFirstName(user.getProfile().getFirstName());
				foundUser.getProfile().setLastName(user.getProfile().getLastName());
				foundUser.getProfile().setEmail(user.getProfile().getEmail());
				foundUser.getProfile().setPhone(user.getProfile().getPhone());
				
				return userMapper.entityToDto(userRepository.saveAndFlush(foundUser));
			}
			else {
				throw new NotAuthorizedException("Password does not match for user: '" + foundUser.getCredentials().getUsername() + "'");
			}
		}
		
		return userMapper.entityToDto(userRepository.saveAndFlush(user));
	}

	/**
	 * Takes in a string to search for and return a username.
	 * 
	 * @return userResponseDto containing only data to send to client
	 */
	@Override
	public UserResponseDto getUser(String userName) {
		return userMapper.entityToDto(findNotDeletedUser(userName));
	}

	/**
	 * Takes in a userRequestDto to verify credentials and a string to change username to.
	 * 
	 * @return userResponseDto containing only data to send to client
	 */
	@Override
	public UserResponseDto updateUser(UserRequestDto userRequestDto, String userName) {		
		
		User userInput = userMapper.requestDtoToEntity(userRequestDto);
		if(userInput.getCredentials() == null || userInput.getProfile() == null || userInput.getCredentials().getUsername() == null || userInput.getCredentials().getPassword() == null || userName == null) {
			throw new BadRequestException("An account username with password to access and a username to change are required.");
		}
		User searchedUser = findNotDeletedUser(userName);
		
		if(userInput.getCredentials().getPassword().equals(searchedUser.getCredentials().getPassword())) {
			
			if(userInput.getProfile().getFirstName() != null) {
				searchedUser.getProfile().setFirstName(userInput.getProfile().getFirstName());
			}
			if(userInput.getProfile().getLastName() != null) {
				searchedUser.getProfile().setLastName(userInput.getProfile().getLastName());
			}
			if(userInput.getProfile().getEmail() != null) {
				searchedUser.getProfile().setEmail(userInput.getProfile().getEmail());
			}
			if(userInput.getProfile().getPhone() != null) {
				searchedUser.getProfile().setPhone(userInput.getProfile().getPhone());
			}
				
			return userMapper.entityToDto(userRepository.saveAndFlush(searchedUser));
		}
		else {
			throw new NotAuthorizedException("Password does not match for user: '" + searchedUser.getCredentials().getUsername() + "'");
		}
	}

	/**
	 * Verifies user and if verified deletes username passed in URL.
	 * 
	 * @return userResponseDto containing only data to send to client
	 */
	@Override
	public UserResponseDto deleteUser(CredentialsDto credentialsDto, String userName) {

		if(credentialsDto == null || credentialsDto.getUsername() == null || credentialsDto.getPassword() == null || userName == null) {
			throw new BadRequestException("Username and password are required.");
		}
		
		User searchedUser = findNotDeletedUser(userName);
		
		if(credentialsDto.getPassword().equals(searchedUser.getCredentials().getPassword())) {
			
			searchedUser.setDeleted(true);
			
			//TODO Set all user's tweets to deleted also
			
			return userMapper.entityToDto(userRepository.saveAndFlush(searchedUser));
		}
		else {
			throw new NotAuthorizedException("Password does not match for user: '" + searchedUser.getCredentials().getUsername() + "'");
		}
	}

	/**
	 * Takes in a string to find a User, then returns a list of all users following the selected user.
	 * 
	 * @param userName of the user to return from
	 * @return List of userReponseDtos of followers
	 */
	@Override
	public List<UserResponseDto> getFollowers(String userName) {
		User user = findNotDeletedUser(userName);
		List<UserResponseDto> followerDtos = new ArrayList<>();
		for(User follower : user.getFollowers()) {
			if(follower.getDeleted() == false) {
				followerDtos.add(userMapper.entityToDto(follower));
			}
		}
		return followerDtos;
	}

}
