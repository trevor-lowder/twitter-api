package com.cooksys.assessment1.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.assessment1.entities.Hashtag;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.repositories.HashtagRepository;
import com.cooksys.assessment1.repositories.UserRepository;
import com.cooksys.assessment1.services.ValidateService;

import lombok.RequiredArgsConstructor;

/**
* The ValidateServiceImpl class is the implementation of the ValidateService interface.
* It handles method calls from the UValidateController to handle the logic
* of the application and make calls further inside the application layers.
* 
* @author Scott VanBrunt
*
*/
@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService{
	
	private final UserRepository userRepository;
	private final HashtagRepository hashtagRepository;
	
	/**
	 * 
	 * @param userName String
	 * @return Boolean true if a User is found, false otherwise
	 */
	private Boolean userExists(String userName){
		Optional<User> user = userRepository.findByCredentialsUsername(userName);
		if(user.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Finds a User by username.
	 * Returns true if found, false otherwise.
	 */
	@Override
	public Boolean ifUserNameExists(String userName) {
		return userExists(userName);
	}

	/**
	 * Finds a User by username and if not deleted.
	 * Returns true if found, false otherwise.
	 */
	@Override
	public Boolean ifUserNameAvailable(String userName) {
		return !userExists(userName);
	}

	@Override
	public Boolean ifTagExists(String label) {
		Hashtag h = hashtagRepository.findByLabel(label);
		
		

		
		if(h.equals(null)) {
			return false;
		}
		return true;
	}

}
