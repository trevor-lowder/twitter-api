package com.cooksys.assessment1.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.services.UserService;

import lombok.RequiredArgsConstructor;

/**
 * The UserController class represents the end-point interacted with from
 * outside the application. Maps functions further within the application to
 * end-point urls.
 * 
 * All requests are sent to the UserService interface for handling.
 * 
 * @author Scott VanBrunt
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	/**
	 * 
	 * @return all not deleted Users in a List of UserResponseDtos.
	 */
	@GetMapping
	public List<UserResponseDto> getAllUsers() {
		return userService.getAllUsers();
	}

	/**
	 * 
	 * @param userRequestDto containing a CredentialsDto and ProfileDto.
	 * @return userResponseDto containing only data to send to client.
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
		
		return userService.createUser(userRequestDto);
	}

}
