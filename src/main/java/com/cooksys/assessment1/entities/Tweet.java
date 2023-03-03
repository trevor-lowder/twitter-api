package com.cooksys.assessment1.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tweet")
public class Tweet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "author")
	private User author;

	@Column(name = "posted")
	@CreationTimestamp
	private Timestamp posted;

	@Column(name = "deleted")
	private boolean deleted = false;

	@Column(name = "content")
	private String content;

	@ManyToOne
	@JoinColumn(name = "inReplyTo")
	private Tweet inReplyTo;

	@OneToMany(mappedBy = "inReplyTo")

    private List<Tweet> replies;

	@ManyToOne
	@JoinColumn(name = "repostOf")
	private Tweet repostOf;

	@OneToMany(mappedBy = "repostOf")

    private List<Tweet> reposts;

	@ManyToMany()
	private List<Hashtag> hashtags;

	@ManyToMany(mappedBy = "likedTweets")
	private List<User> likedBy;

	@ManyToMany(mappedBy = "mentionedTweets")
	private List<User> mentionedBy;

}
