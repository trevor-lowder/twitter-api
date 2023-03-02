package com.cooksys.assessment1.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.exceptions.BadRequestException;
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
		
		
		//FIXME This method currently throws the BadRequestException on all post requests.
		// All values in the userRequestDto log as null.
		// Problem seems to be somewhere between here and the endpoint in UserController where the RequestBody is taken in.
		//TODO Remove this log

		User user = userMapper.requestDtoToEntity(userRequestDto);

		if(user.getCredentials() == null ||
				user.getCredentials().getUsername() == null ||
				user.getCredentials().getPassword() == null ||
				user.getProfile() == null ||
				user.getProfile().getEmail() == null) {
			throw new BadRequestException("Username, Password, and Email are required fields.");
		}
		
		
		return user;
	}
	
	/**
	 * 
	 * @return all not deleted Users in a List of UserResponseDtos.
	 */
	@Override
	public List<UserResponseDto> getAllUsers() {
		return userMapper.entitiesToDtos(userRepository.findByDeletedFalse());
	}

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {

		User user = validateAndConvertUserInput(userRequestDto);
		
		// Check is username exists, if exists and not deleted throw exception, if exists and deleted toggle deleted flag and update other fields
		Optional<User> searchedUser = userRepository.findByCredentialsUsername(user.getCredentials().getPassword());
		if(!searchedUser.isEmpty()) {
			User foundUser = searchedUser.get();
			if(foundUser.getDeleted() == false) {
				throw new BadRequestException("The given username is already in use.");
			}
			else {
				foundUser.setDeleted(true);
				foundUser.getCredentials().setPassword(user.getCredentials().getPassword());;
				foundUser.getProfile().setFirstName(user.getProfile().getFirstName());
				foundUser.getProfile().setLastName(user.getProfile().getLastName());
				foundUser.getProfile().setEmail(user.getProfile().getEmail());
				foundUser.getProfile().setPhone(user.getProfile().getPhone());
				
				return userMapper.entityToDto(userRepository.saveAndFlush(foundUser));
			}
		}
		
		return userMapper.entityToDto(userRepository.saveAndFlush(user));
	}

}
