package com.cooksys.assessment1.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.assessment1.dtos.CredentialsDto;
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
	 * Takes a userRequestDto containing a CredentialsDto and ProfileDto.
	 * 
	 * @return userResponseDto containing only data to send to client.
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
		return userService.createUser(userRequestDto);
	}

	/**
	 * 
	 * @param userName String from the request URL to search for
	 * @return userResponseDto containing only data to send to client
	 */
	@GetMapping("/@{userName}")
	public UserResponseDto getUser(@PathVariable String userName) {
		return userService.getUser(userName);
	}

	/**
	 * Takes in a userRequestDto to verify credentials and change username.
	 * 
	 * @param userName String to change selected username to.
	 * @return userResponseDto containing only data to send to client
	 */
	@PatchMapping("/@{userName}")
	@ResponseStatus(HttpStatus.OK)
	public UserResponseDto renameUser(@RequestBody UserRequestDto userRequestDto, @PathVariable String userName) {
		return userService.renameUser(userRequestDto, userName);
	}

	/**
	 * Verifies user and if verified deletes username passed in URL.
	 * 
	 * @param credentialsDto to verify user
	 * @param userName       of user to delete
	 * @return userResponseDto containing only data to send to client
	 */
	@DeleteMapping("/@{userName}")
	@ResponseStatus(HttpStatus.OK)
	public UserResponseDto deleteUser(@RequestBody CredentialsDto credentialsDto, @PathVariable String userName) {
		return userService.deleteUser(credentialsDto, userName);
	}

}
