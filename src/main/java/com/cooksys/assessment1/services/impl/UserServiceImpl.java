package com.cooksys.assessment1.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.assessment1.dtos.CredentialsDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.exceptions.BadRequestException;
import com.cooksys.assessment1.exceptions.NotAuthorizedException;
import com.cooksys.assessment1.exceptions.NotFoundException;
import com.cooksys.assessment1.mappers.TweetMapper;
import com.cooksys.assessment1.mappers.UserMapper;
import com.cooksys.assessment1.repositories.TweetRepository;
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
	private final TweetRepository tweetRepository;
	private final TweetMapper tweetMapper;

	/**
	 * 
	 * 
	 * @param userRequestDto
	 * @return User
	 */
	private User validateAndConvertUserInput(UserRequestDto userRequestDto) {

		User user = userMapper.requestDtoToEntity(userRequestDto);

		if (user.getCredentials() == null || user.getCredentials().getUsername() == null
				|| user.getCredentials().getPassword() == null || user.getProfile() == null
				|| user.getProfile().getEmail() == null) {
			throw new BadRequestException("Username, Password, and Email are required fields.");
		}

		return user;
	}

	private User findNotDeletedUser(String userName) {

		Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(userName);

		if (user.isEmpty()) {
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
	 * Takes in a userRequestDto, sends it off for validation and conversion to a
	 * User, then searches the userRepository for a matching username. If match
	 * found tests deleted flag. If deleted and password matches updates deleted
	 * user with dto data and marked deleted false. If password doesn't match throws
	 * NotAuthorizedException. If no matching username is found creates a new user
	 * and pushes to the database.
	 * 
	 * @return a userResponseDto of an updated user or a newly added user.
	 */
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {

		User user = validateAndConvertUserInput(userRequestDto);

		// Check is username exists, if exists and not deleted throw exception, if
		// exists and deleted toggle deleted flag and update other fields
		Optional<User> searchedUser = userRepository.findByCredentialsUsername(user.getCredentials().getUsername());
		if (!searchedUser.isEmpty()) {
			User foundUser = searchedUser.get();
			if (foundUser.getDeleted() == false) {
				throw new BadRequestException("The given username is already in use.");
			} else if (foundUser.getCredentials().getPassword().equals(user.getCredentials().getPassword())) {
				foundUser.setDeleted(false);
				foundUser.getCredentials().setPassword(user.getCredentials().getPassword());
				;
				foundUser.getProfile().setFirstName(user.getProfile().getFirstName());
				foundUser.getProfile().setLastName(user.getProfile().getLastName());
				foundUser.getProfile().setEmail(user.getProfile().getEmail());
				foundUser.getProfile().setPhone(user.getProfile().getPhone());

				return userMapper.entityToDto(userRepository.saveAndFlush(foundUser));
			} else {
				throw new NotAuthorizedException(
						"Password does not match for user: '" + foundUser.getCredentials().getUsername() + "'");
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
	 * Takes in a userRequestDto to verify credentials and a string to change
	 * username to.
	 * 
	 * @return userResponseDto containing only data to send to client
	 */
	@Override
	public UserResponseDto updateUser(UserRequestDto userRequestDto, String userName) {

		User userInput = userMapper.requestDtoToEntity(userRequestDto);
		if (userInput.getCredentials() == null || userInput.getProfile() == null
				|| userInput.getCredentials().getUsername() == null || userInput.getCredentials().getPassword() == null
				|| userName == null) {
			throw new BadRequestException(
					"An account username with password to access and a username to change are required.");
		}
		User searchedUser = findNotDeletedUser(userName);

		if (userInput.getCredentials().getPassword().equals(searchedUser.getCredentials().getPassword())) {

			if (userInput.getProfile().getFirstName() != null) {
				searchedUser.getProfile().setFirstName(userInput.getProfile().getFirstName());
			}
			if (userInput.getProfile().getLastName() != null) {
				searchedUser.getProfile().setLastName(userInput.getProfile().getLastName());
			}
			if (userInput.getProfile().getEmail() != null) {
				searchedUser.getProfile().setEmail(userInput.getProfile().getEmail());
			}
			if (userInput.getProfile().getPhone() != null) {
				searchedUser.getProfile().setPhone(userInput.getProfile().getPhone());
			}

			return userMapper.entityToDto(userRepository.saveAndFlush(searchedUser));
		} else {
			throw new NotAuthorizedException(
					"Password does not match for user: '" + searchedUser.getCredentials().getUsername() + "'");
		}
	}

	/**
	 * Verifies user and if verified deletes username passed in URL.
	 * 
	 * @return userResponseDto containing only data to send to client
	 */
	@Override
	public UserResponseDto deleteUser(CredentialsDto credentialsDto, String userName) {

		if (credentialsDto == null || credentialsDto.getUsername() == null || credentialsDto.getPassword() == null
				|| userName == null) {
			throw new BadRequestException("Username and password are required.");
		}

		User searchedUser = findNotDeletedUser(userName);

		if (credentialsDto.getPassword().equals(searchedUser.getCredentials().getPassword())) {

			searchedUser.setDeleted(true);

			// Get tweets by selected user, set to deleted, add to a List and push list to
			// TweetRepository
			List<Tweet> tweets = new ArrayList<>();
			for (Tweet t : searchedUser.getTweets()) {
				t.setDeleted(true);
				tweets.add(t);
			}
			tweetRepository.saveAllAndFlush(tweets);

			return userMapper.entityToDto(userRepository.saveAndFlush(searchedUser));
		} else {
			throw new NotAuthorizedException(
					"Password does not match for user: '" + searchedUser.getCredentials().getUsername() + "'");
		}
	}

	/**
	 * Takes in a string to find a User, then returns a list of all users following
	 * the selected user.
	 * 
	 * @param userName of the user to return from
	 * @return List of userReponseDtos of followers
	 */
	@Override
	public List<UserResponseDto> getFollowers(String userName) {
		User user = findNotDeletedUser(userName);
		List<UserResponseDto> followerDtos = new ArrayList<>();
		for (User follower : user.getFollowers()) {
			if (follower.getDeleted() == false) {
				followerDtos.add(userMapper.entityToDto(follower));
			}
		}
		return followerDtos;
	}

	/**
	 * Takes in a string to find a User, then returns a list of all users followed
	 * by selected user.
	 * 
	 * @param userName of the user to return from
	 * @return List of userReponseDtos of following users
	 */
	@Override
	public List<UserResponseDto> getFollowing(String userName) {
		User user = findNotDeletedUser(userName);
		List<UserResponseDto> followingDtos = new ArrayList<>();
		for (User following : user.getFollowing()) {
			if (following.getDeleted() == false) {
				followingDtos.add(userMapper.entityToDto(following));
			}
		}
		return followingDtos;
	}

	/**
	 * Received credentials to access a User and adds the User linked to the
	 * provided username to their followed list.
	 * 
	 * @param credentialsDto to verify user
	 * @param userName       to follow
	 */
	@Override
	public void follow(CredentialsDto credentialsDto, String userName) {
		if (credentialsDto == null || credentialsDto.getUsername() == null || credentialsDto.getPassword() == null
				|| userName == null) {
			throw new BadRequestException("Username and password are required.");
		}
		User userToFollow = findNotDeletedUser(userName);
		User user = findNotDeletedUser(credentialsDto.getUsername());
		if (credentialsDto.getPassword().equals(user.getCredentials().getPassword())) {
			if (user.getFollowing().contains(userToFollow)) {
				throw new NotFoundException("The supplied user is already following this user.");
			} else {

				// FIXME Does not add any entries in the database
				// TOOD Remove logs
				List<User> following = user.getFollowing();

				// System.out.println(following.size());
				following.add(userToFollow);

				// System.out.println(following.size());
				user.setFollowing(List.copyOf(following));

				// System.out.println();
				userRepository.saveAllAndFlush(user.getFollowing());
			}
		} else {
			throw new NotAuthorizedException(
					"Password does not match for user: '" + user.getCredentials().getUsername() + "'");
		}
	}

	/**
	 * Received credentials to access a User and removes the User linked to the
	 * provided username from their followed list.
	 * 
	 * @param credentialsDto to verify user
	 * @param userName       to unfollow
	 */
	@Override
	public void unfollow(CredentialsDto credentialsDto, String userName) {
		if (credentialsDto == null || credentialsDto.getUsername() == null || credentialsDto.getPassword() == null
				|| userName == null) {
			throw new BadRequestException("Username and password are required.");
		}
		User userToFollow = findNotDeletedUser(userName);
		User user = findNotDeletedUser(credentialsDto.getUsername());
		if (credentialsDto.getPassword().equals(user.getCredentials().getPassword())) {
			if (user.getFollowing().contains(userToFollow)) {

				// FIXME Does not add any entries in the database
				user.getFollowing().remove(userToFollow);
				userRepository.saveAndFlush(user);
			} else {
				throw new NotFoundException("The supplied user is not following this user.");
			}
		} else {
			throw new NotAuthorizedException(
					"Password does not match for user: '" + user.getCredentials().getUsername() + "'");
		}
	}

	/**
	 * Takes a string to find a User and returns a List of all Tweets the user is
	 * mentioned in reverse-chronological order.
	 * 
	 * @param userName string to search for a User by
	 * @return List of TweetResponseDtos
	 */
	@Override
	public List<TweetResponseDto> getMentions(String userName) {
		User user = findNotDeletedUser(userName);
		List<Tweet> mentions = new ArrayList<>();
		for (Tweet t : user.getMentionedTweets()) {
			if (!t.isDeleted()) {
				mentions.add(t);
			}			
		}
		// mentions.sort(Comparator.comparing(Tweet::getPosted));
		Collections.sort(mentions, Collections.reverseOrder(Comparator.comparing(Tweet::getPosted)));
		
		return tweetMapper.entitiesToResponseDtos(mentions);
	}

	/**
	 * Takes a string to find a User and returns a List of all Tweets the user has
	 * posted in reverse-chronological order.
	 * 
	 * @param userName string to search for a User by
	 * @return List of TweetResponseDtos
	 */
	@Override
	public List<TweetResponseDto> getTweets(String userName) {
		User user = findNotDeletedUser(userName);
		List<Tweet> tweets = new ArrayList<>();
		for (Tweet t : user.getTweets()) {
			if (!t.isDeleted()) {
				tweets.add(t);
			}			
		}
		// mentions.sort(Comparator.comparing(Tweet::getPosted));
		Collections.sort(tweets, Collections.reverseOrder(Comparator.comparing(Tweet::getPosted)));
		
		return tweetMapper.entitiesToResponseDtos(tweets);
	}

	/**
	 * Takes a string to find a User and returns a List of all Tweets the user has
	 * posted and those posted by followed users in reverse-chronological order.
	 * 
	 * @param userName string to search for a User by
	 * @return List of TweetResponseDtos
	 */
	@Override
	public List<TweetResponseDto> getFeed(String userName) {
		User user = findNotDeletedUser(userName);
		List<Tweet> tweets = new ArrayList<>();
		for (Tweet t : user.getTweets()) {
			if (!t.isDeleted()) {
				tweets.add(t);
			}			
		}
		for(User u : user.getFollowing()) {
			for(Tweet t : u.getTweets()) {
				if (!t.isDeleted()) {
					tweets.add(t);
				}
			}
		}		
		// mentions.sort(Comparator.comparing(Tweet::getPosted));
		Collections.sort(tweets, Collections.reverseOrder(Comparator.comparing(Tweet::getPosted)));
		
		return tweetMapper.entitiesToResponseDtos(tweets);
	}

}
