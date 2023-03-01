package com.cooksys.assessment1.services;

import java.util.List;

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

}
