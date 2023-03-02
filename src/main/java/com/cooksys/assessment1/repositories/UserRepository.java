package com.cooksys.assessment1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.assessment1.entities.User;

/**
 * A Repository for User entities used for Spring Boot code generation. Used to
 * interact with data in the database.
 * 
 * @author Scott VanBrunt
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	// Derived Query to find a Quiz by id and put in a wrapper to prevent errors
//		Optional<Quiz> findById(Long id);
	
	// Derived Query to find all not deleted users
	List<User> findByDeletedFalse();

}
