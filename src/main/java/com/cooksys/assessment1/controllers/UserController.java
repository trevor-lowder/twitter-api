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
import com.cooksys.assessment1.dtos.TweetResponseDto;
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
	public UserResponseDto updateUser(@RequestBody UserRequestDto userRequestDto, @PathVariable String userName) {
		return userService.updateUser(userRequestDto, userName);
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

	/**
	 * Takes in a string to find a User, then returns a list of all users following
	 * the selected user.
	 * 
	 * @param userName of the user to return from
	 * @return List of userReponseDtos of followers
	 */
	@GetMapping("/@{userName}/followers")
	public List<UserResponseDto> getFollowers(@PathVariable String userName) {
		return userService.getFollowers(userName);
	}

	/**
	 * Takes in a string to find a User, then returns a list of all users followed
	 * by selected user.
	 * 
	 * @param userName of the user to return from
	 * @return List of userReponseDtos of following users
	 */
	@GetMapping("/@{userName}/following")
	public List<UserResponseDto> getFollowing(@PathVariable String userName) {
		return userService.getFollowing(userName);
	}

	/**
	 * Received credentials to access a User and adds the User linked to the
	 * provided username to their followed list.
	 * 
	 * @param credentialsDto to verify user
	 * @param userName       to follow
	 */
	@PostMapping("/@{userName}/follow")
	@ResponseStatus(HttpStatus.OK)
	public void follow(@RequestBody CredentialsDto credentialsDto, @PathVariable String userName) {
		userService.follow(credentialsDto, userName);
	}

	/**
	 * Received credentials to access a User and removes the User linked to the
	 * provided username from their followed list.
	 * 
	 * @param credentialsDto to verify user
	 * @param userName       to unfollow
	 */
	@PostMapping("/@{userName}/unfollow")
	@ResponseStatus(HttpStatus.OK)
	public void unfollow(@RequestBody CredentialsDto credentialsDto, @PathVariable String userName) {
		userService.unfollow(credentialsDto, userName);
	}

	/**
	 * Takes a string to find a User and returns a List of all Tweets the user is
	 * mentioned in reverse-chronological order.
	 * 
	 * @param userName string to search for a User by
	 * @return List of TweetResponseDtos
	 */
	@GetMapping("/@{userName}/mentions")
	public List<TweetResponseDto> getMentions(@PathVariable String userName) {
		return userService.getMentions(userName);
	}

	/**
	 * Takes a string to find a User and returns a List of all Tweets the user has
	 * posted in reverse-chronological order.
	 * 
	 * @param userName string to search for a User by
	 * @return List of TweetResponseDtos
	 */
	@GetMapping("/@{userName}/tweets")
	public List<TweetResponseDto> getTweets(@PathVariable String userName) {
		return userService.getTweets(userName);
	}

	/**
	 * Takes a string to find a User and returns a List of all Tweets the user has
	 * posted and those posted by followed users in reverse-chronological order.
	 * 
	 * @param userName string to search for a User by
	 * @return List of TweetResponseDtos
	 */
	@GetMapping("/@{userName}/feed")
	public List<TweetResponseDto> getFeed(@PathVariable String userName) {
		return userService.getFeed(userName);
	}

}
