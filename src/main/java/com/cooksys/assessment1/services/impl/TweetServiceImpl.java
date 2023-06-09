package com.cooksys.assessment1.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import org.springframework.stereotype.Service;

import com.cooksys.assessment1.dtos.CredentialsDto;
import com.cooksys.assessment1.dtos.HashtagDto;
import com.cooksys.assessment1.dtos.TweetContextDto;
import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.Hashtag;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.exceptions.BadRequestException;
import com.cooksys.assessment1.exceptions.NotAuthorizedException;
import com.cooksys.assessment1.exceptions.NotFoundException;
import com.cooksys.assessment1.mappers.HashtagMapper;
import com.cooksys.assessment1.mappers.TweetMapper;
import com.cooksys.assessment1.mappers.UserMapper;
import com.cooksys.assessment1.repositories.HashtagRepository;
import com.cooksys.assessment1.repositories.TweetRepository;
import com.cooksys.assessment1.repositories.UserRepository;
import com.cooksys.assessment1.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	private final TweetRepository tweetRepository;
	private final TweetMapper tweetMapper;
	private final UserMapper userMapper;
	private final HashtagMapper hashtagMapper;
	private final UserRepository userRepository;
	private final HashtagRepository hashtagRepository;

	/**
	 * Checks that credentials exist, searches for a matching active user and
	 * validates passed in password against database stored password.
	 * 
	 * @param credentialsDto to verify an active user
	 * @return user
	 */
	private User ValidateUser(CredentialsDto credentialsDto) {

		if (credentialsDto == null || credentialsDto.getUsername() == null || credentialsDto.getPassword() == null) {
			throw new BadRequestException("Username and password are required.");
		}

		Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(credentialsDto.getUsername());
		if (user.isEmpty()) {
			throw new NotFoundException("No user found by username '" + credentialsDto.getUsername() + "'");
		}

		if (credentialsDto.getPassword().equals(user.get().getCredentials().getPassword())) {
			return user.get();
		} else {
			throw new NotAuthorizedException(
					"Password does not match for user: '" + user.get().getCredentials().getUsername() + "'");
		}
	}

	private Tweet getValidTweet(Long tweetId) throws NotFoundException {
		Tweet tweet = tweetRepository.findById(tweetId)
				.orElseThrow(() -> new NotFoundException("Tweet not found with id: " + tweetId));
		if (tweet.isDeleted()) {
			throw new NotFoundException("Tweet with id " + tweetId + " is deleted");
		}
		return tweet;
	}

	public List<String> parseHashtags(String content) {
		List<String> hashtags = new ArrayList<>();
		String[] words = content.split("\\s+");
		for (String word : words) {
			if (word.startsWith("#")) {
				hashtags.add(word.substring(1));
			}
		}
		return hashtags;
	}

	public List<String> parseMentions(String content) {
		List<String> mentions = new ArrayList<>();
		String[] words = content.split("\\s+");
		for (String word : words) {
			if (word.startsWith("@")) {
				mentions.add(word.substring(1));
			}
		}
		return mentions;
	}

	/**
	 * Takes in a tweetRequestDto, sends the credentials to a private method for
	 * validation, checks there is content for a new tweet, creates a new tweet and
	 * assigns the returned validated user as the author. Parses the tweet content
	 * for "@mentions" and "#hashtags" which are added to the database. Saves the
	 * tweet to the database and returns a tweetResponseDto.
	 */
	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {

		User user = ValidateUser(tweetRequestDto.getCredentials());

		if (tweetRequestDto.getContent() == null) {
			throw new BadRequestException("Tweet Content is required.");
		}

		Tweet tweet = tweetMapper.requestDtoToEntity(tweetRequestDto);
		tweet.setAuthor(user);
		tweet.setMentionedBy(new ArrayList<User>());
		tweet = tweetRepository.saveAndFlush(tweet);

		// Finds and adds hashtags

		List<String> tagStrings = parseHashtags(tweet.getContent());
		List<Hashtag> tags = new ArrayList<>();
		for (String s : tagStrings) {

			Optional<Hashtag> searchedTag = hashtagRepository.findByLabel(s);
			if (searchedTag.isEmpty()) {
				Hashtag tag = new Hashtag();
				tag.setLabel(s);
				tags.add(tag);
				tag.setLastUsed(tweet.getPosted());
				hashtagRepository.saveAndFlush(tag);
			} else {
				searchedTag.get().setLastUsed(tweet.getPosted());
				tags.add(searchedTag.get());
			}

		}
		tweet.setHashtags(tags);

		// Finds and adds mentions

		List<String> mentionStrings = parseMentions(tweet.getContent());
		List<User> mentions = new ArrayList<>();
		for (String s : mentionStrings) {

			Optional<User> searchedMention = userRepository.findByCredentialsUsernameAndDeletedFalse(s);

			if (!searchedMention.isEmpty()) {
				if (!(tweet.getMentionedBy().contains(searchedMention.get()))) {
					searchedMention.get().getMentionedTweets().add(tweet);
					mentions.add(searchedMention.get());

				}
			}
//        	
//        	if(tweet.getMentionedBy().contains(searchedMention.get())) {
//        		User tag = new Hashtag();
//        		tag.setLabel(s);
//            	tags.add(tag);
//            	tag.setLastUsed(tweet.getPosted());        		
//        		hashtagRepository.saveAndFlush(tag);
//        	}
//        	else {
//        		searchedMention.get().setLastUsed(tweet.getPosted());
//        		tags.add(searchedMention.get());
//        	}        	

		}
		userRepository.saveAllAndFlush(mentions);

		return tweetMapper.entityToResponseDto(tweetRepository.saveAndFlush(tweet));
	}

	public List<TweetResponseDto> getAllNonDeletedTweets() {
		List<Tweet> tweets = tweetRepository.findByDeletedFalseOrderByPostedDesc();
		return tweetMapper.entitiesToResponseDtos(tweets);
	}

	public TweetResponseDto getTweetById(Long id) {
		Tweet tweet = getValidTweet(id);
		return tweetMapper.entityToResponseDto(tweet);
	}

	public TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto) {
		ValidateUser(credentialsDto);
		Tweet tweet = getValidTweet(id);
		tweet.setDeleted(true);

		return tweetMapper.entityToResponseDto(tweetRepository.saveAndFlush(tweet));
	}

	/**
	 * Takes in a credentialsDto and sends it for validation. Then finds tweet by
	 * the passed in id and if found, adds a like relationship between the tweet and
	 * the user.
	 */
	public void createLike(Long tweetId, CredentialsDto credentialsDto) {

		User user = ValidateUser(credentialsDto);
		Tweet tweet = getValidTweet(tweetId);

		if (!(tweet.getLikedBy().contains(user))) {
			user.getLikedTweets().add(tweet);
			userRepository.saveAndFlush(user);
		}
	}

	/**
	 * Takes in a tweetRequestDto, send the credentials for validation and sends for
	 * a tweet by id. Then sets up a new tweet, sets the found tweet as inReplyTo,
	 * sets the validated user as author, saves to the database and returns a
	 * tweetResponseDto.
	 */
	public TweetResponseDto createReply(Long tweetId, TweetRequestDto tweetRequestDto) {

		User user = ValidateUser(tweetRequestDto.getCredentials());
		Tweet tweet = getValidTweet(tweetId);

		if (tweetRequestDto.getContent() == null) {
			throw new BadRequestException("Tweet Content is required.");
		}

		Tweet newTweet = tweetMapper.requestDtoToEntity(tweetRequestDto);
		newTweet.setAuthor(user);
		newTweet.setInReplyTo(tweet);

		newTweet = tweetRepository.saveAndFlush(newTweet);

		// Finds and adds hashtags

		List<String> tagStrings = parseHashtags(newTweet.getContent());
		List<Hashtag> tags = new ArrayList<>();
		for (String s : tagStrings) {

			Optional<Hashtag> searchedTag = hashtagRepository.findByLabel(s);
			if (searchedTag.isEmpty()) {
				Hashtag tag = new Hashtag();
				tag.setLabel(s);
				tags.add(tag);
				tag.setLastUsed(newTweet.getPosted());
				hashtagRepository.saveAndFlush(tag);
			} else {
				searchedTag.get().setLastUsed(newTweet.getPosted());
				tags.add(searchedTag.get());
			}

		}
		newTweet.setHashtags(tags);

		// TODO Add parsing methods to find mentions and hashtags in tweet content.

		return tweetMapper.entityToResponseDto(tweetRepository.saveAndFlush(newTweet));
	}

	public TweetResponseDto createRepost(Long tweetId, CredentialsDto credentialsDto) {
		User user = ValidateUser(credentialsDto);
		Tweet tweet = getValidTweet(tweetId);
		Tweet repost = new Tweet();
		repost.setAuthor(user);
		repost.setRepostOf(tweet);
		repost = tweetRepository.save(repost);
		return tweetMapper.entityToResponseDto(repost);
	}

	public List<UserResponseDto> getLikes(Long id) {
		Tweet tweet = getValidTweet(id);
		List<User> likedBy = tweet.getLikedBy();
		List<UserResponseDto> userResponseDtos = new ArrayList<>();
		for (User user : likedBy) {
			if (!user.isDeleted()) {
				userResponseDtos.add(userMapper.entityToDto(user));
			}
		}
		return userResponseDtos;
	}

	public TweetContextDto getContext(Long tweetId) {
		Tweet targetTweet = getValidTweet(tweetId);
		List<Tweet> beforeList = new ArrayList<>();
		List<Tweet> afterList = new ArrayList<>();

		// Traverse up the reply chain and add tweets to the beforeList
		Tweet parentTweet = targetTweet.getInReplyTo();
		while (parentTweet != null) {
			if (!parentTweet.isDeleted()) {
				beforeList.add(parentTweet);
			}
			parentTweet = parentTweet.getInReplyTo();
		}

		// Traverse down the reply chain and add tweets to the afterList
		Stack<Tweet> afterStack = new Stack<>();
		for (Tweet reply : targetTweet.getReplies()) {
			if (!reply.isDeleted()) {
				afterStack.push(reply);
				while (!afterStack.isEmpty()) {
					Tweet currReply = afterStack.pop();
					afterList.add(currReply);
					for (Tweet nestedReply : currReply.getReplies()) {
						if (!nestedReply.isDeleted()) {
							afterStack.push(nestedReply);
						}
					}
				}
			}
		}

		return tweetMapper.entityToContextDto(targetTweet, beforeList, afterList);
	}

	public List<TweetResponseDto> getReplies(Long id) {
		Tweet tweet = getValidTweet(id);
		List<Tweet> replies = tweetRepository.findByInReplyToAndDeletedFalseOrderByPostedDesc(tweet);
		return tweetMapper.entitiesToResponseDtos(replies);
	}

	public List<TweetResponseDto> getReposts(Long id) {
		Tweet tweet = getValidTweet(id);
		List<Tweet> reposts = tweetRepository.findByRepostOfAndDeletedFalseOrderByPostedDesc(tweet);
		return tweetMapper.entitiesToResponseDtos(reposts);
	}

	public List<UserResponseDto> getMentions(Long id) {
		Tweet tweet = getValidTweet(id);
		List<User> mentionedUsers = new ArrayList<>();
		for (User user : tweet.getMentionedBy()) {
			if (!user.isDeleted()) {
				mentionedUsers.add(user);
			}
		}
		return userMapper.entitiesToDtos(mentionedUsers);
	}

	@Override
	public List<HashtagDto> getHashtagsFromTweetId(Long id) {
		Tweet tweet = getValidTweet(id);
		return hashtagMapper.entitiesToDtos(tweet.getHashtags());
	}
}
