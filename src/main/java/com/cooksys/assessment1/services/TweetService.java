package com.cooksys.assessment1.services;

import java.util.List;
import com.cooksys.assessment1.dtos.*;

public interface TweetService {

    TweetResponseDto createTweet(TweetRequestDto TweetRequestDto);

    List<TweetResponseDto> getAllNonDeletedTweets();

    TweetResponseDto getTweetById(Long id);

    TweetResponseDto deleteTweet(Long id);

    void createLike(Long tweetId, UserRequestDto UserRequestDto);

    TweetResponseDto createReply(Long tweetId, TweetRequestDto TweetRequestDto);

    TweetResponseDto createRepost(Long tweetId, UserRequestDto UserRequestDto);

    List<UserResponseDto> getLikes(Long tweetId);

    TweetContextDto getContext(Long tweetId);

    List<TweetResponseDto> getReplies(Long tweetId);

    List<TweetResponseDto> getReposts(Long tweetId);

    List<UserResponseDto> getMentions(Long tweetId);

	List<HashtagDto> getHashtagsFromTweetId(Long id);
}
