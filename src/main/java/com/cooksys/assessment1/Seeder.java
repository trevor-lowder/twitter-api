package com.cooksys.assessment1;

import com.cooksys.assessment1.entities.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cooksys.assessment1.repositories.HashtagRepository;
import com.cooksys.assessment1.repositories.TweetRepository;
import com.cooksys.assessment1.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Class contains one method, which is used to seed a database with test data.
 * 
 * @author Scott VanBrunt
 *
 */
@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {

	private final UserRepository userRepository;
	private final TweetRepository tweetRepository;
	private final HashtagRepository hashtagRepository;

	/**
	 * This method seeds the database with 2 quizzes. Each quiz has 4 questions and
	 * each question has 4 possible answers. Feel free to edit this seeder, but note
	 * that the order in which you create and save quizzes/questions/answers to the
	 * database does matter.
	 */
	@Override
	public void run(String... args) throws Exception {
		
		User user1 = new User();
		Credentials cred1 = new Credentials();
		cred1.setUserName("user1");
		cred1.setPassword("password");
		user1.setCredentials(cred1);
		Profile prof1 = new Profile();
		prof1.setFirstName("first");
		prof1.setLastName("last");
		prof1.setEmail("1.email");
		prof1.setPhone("999-999-9999");
		user1.setProfile(prof1);
		
		userRepository.saveAndFlush(user1);
	}

}
