package com.cooksys.assessment1.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
