package com.cooksys.assessment1.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

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

	@CreationTimestamp
	private Timestamp joined;

	private Boolean deleted = false;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private List<Tweet> tweets;

	@ManyToMany
	private List<User> followers;

	@ManyToMany(mappedBy = "followers")
	private List<User> following;

	@ManyToMany
	private List<Tweet> likedTweets;

	@ManyToMany
	private List<Tweet> mentionedTweets;
}
