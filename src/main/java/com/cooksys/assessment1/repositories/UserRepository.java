package com.cooksys.assessment1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.assessment1.entities.User;

/**
 * A Repository for User entities used for Spring Boot code generation. Used to
 * interact with data in the database.
 * 
 * @author Scott VanBrunt
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	// Derived Query to find a Quiz by id and put in a wrapper to prevent errors
//		Optional<Quiz> findById(Long id);

}
