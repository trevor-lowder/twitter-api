package com.cooksys.assessment1.services;

import java.util.List;

import com.cooksys.assessment1.dtos.CredentialsDto;
import com.cooksys.assessment1.dtos.HashtagDto;
import com.cooksys.assessment1.dtos.TweetContextDto;
import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;

public interface TweetService {

	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	List<TweetResponseDto> getAllNonDeletedTweets();

	TweetResponseDto getTweetById(Long id);

	TweetResponseDto deleteTweet(Long id);

	void createLike(Long tweetId, CredentialsDto credentialsDto);

	TweetResponseDto createReply(Long tweetId, TweetRequestDto tweetRequestDto);

	TweetResponseDto createRepost(Long tweetId, UserRequestDto userRequestDto);

	List<UserResponseDto> getLikes(Long tweetId);

	TweetContextDto getContext(Long tweetId);

	List<TweetResponseDto> getReplies(Long tweetId);

	List<TweetResponseDto> getReposts(Long tweetId);

	List<UserResponseDto> getMentions(Long tweetId);

	List<HashtagDto> getHashtagsFromTweetId(Long id);
}
