package com.cooksys.assessment1.services;

import java.util.List;

import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;


public interface TweetService {
    
    TweetResponseDto createTweet(TweetRequestDto TweetRequestDto);
    
    List<TweetResponseDto> getAllNonDeletedTweets();
    
    TweetResponseDto getTweetById(Long id);
    
    TweetResponseDto deleteTweet(Long id);
    
    // void createLike(Long tweetId, UserRequestDto UserRequestDto);
    
    TweetResponseDto createReply(Long tweetId, TweetRequestDto TweetRequestDto);
    
    // TweetResponseDto createRepost(Long tweetId, UserRequestDto UserRequestDto);
    
    List<String> getTagsForTweet(Long tweetId);
    
    // List<UserResponseDto> getUsersWhoLikedTweet(Long tweetId);
    
    // ContextResponseDto getTweetContext(Long tweetId);
    
    List<TweetResponseDto> getDirectReplies(Long tweetId);
    
    List<TweetResponseDto> getDirectReposts(Long tweetId);
    
    // List<UserResponseDto> getUsersMentionedInTweet(Long tweetId);
}

