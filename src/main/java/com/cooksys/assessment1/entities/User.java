package com.cooksys.assessment1.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This User class is an Entity class for Spring to create database entries
 * with.
 * 
 * @author Scott VanBrunt
 *
 */
@Table(name = "user_table")
@Entity
@NoArgsConstructor
@Data
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private Credentials credentials;

	@Embedded
	private Profile profile;

	private Timestamp joined;

	private Boolean deleted = false;

	@OneToMany
	private List<User> followers;

	// These two mappings are meant to be the User side of the likes and mentions many to many and will need to be uncommented once the Tweet class and related are in place
	@ManyToMany
	private List<Tweet> likedTweets;
	
	@ManyToMany
	private List<Tweet> mentionedTweets;
}
