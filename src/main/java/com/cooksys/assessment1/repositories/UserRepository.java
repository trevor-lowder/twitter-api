package com.cooksys.assessment1.repositories;

import java.util.List;
import java.util.Optional;

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

	// Derived Query to find a User by id and put in a wrapper to prevent errors
	Optional<User> findById(Long id);

	// Derived Query to find all not deleted users
	List<User> findByDeletedFalse();

	// Derived Query to find a User by username and put in a wrapper to prevent errors
	Optional<User> findByCredentialsUsername(String userName);

	// Derived Query to find a User by username and not deleted and put in a wrapper to prevent errors
	Optional<User> findByCredentialsUsernameAndDeletedFalse(String userName);

}
