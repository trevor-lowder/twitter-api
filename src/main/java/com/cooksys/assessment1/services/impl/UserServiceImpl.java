package com.cooksys.assessment1.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.assessment1.dtos.UserResponseDto;
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
	 * @return all not deleted Users in a List of UserResponseDtos.
	 */
	@Override
	public List<UserResponseDto> getAllUsers() {
		return userMapper.entitiesToDtos(userRepository.findByDeletedFalse());
	}

}
