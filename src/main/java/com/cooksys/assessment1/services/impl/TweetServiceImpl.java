package com.cooksys.assessment1.services.impl;

import java.util.List;

import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.services.TweetService;

// TODO import User DTOs
public class TweetServiceImpl implements TweetService {
    public TweetResponseDto createTweet(TweetRequestDto TweetRequestDto) {
        return null;
    }

    public List<TweetResponseDto> getAllNonDeletedTweets() {
        return null;
    }

    public TweetResponseDto getTweetById(Long id) {
        return null;
    }

    public TweetResponseDto deleteTweet(Long id) {
        return null;
    }

    // public void createLike(Long tweetId, UserRequestDto UserRequestDto){return
    // null;}

    public TweetResponseDto createReply(Long tweetId, TweetRequestDto TweetRequestDto) {
        return null;
    }

    // public TweetResponseDto createRepost(Long tweetId, UserRequestDto
    // UserRequestDto){return null;}

    public List<String> getTagsForTweet(Long tweetId) {
        return null;
    }

    // public List<UserResponseDto> getUsersWhoLikedTweet(Long tweetId){return
    // null;}

    // public ContextResponseDto getTweetContext(Long tweetId){return null;}

    public List<TweetResponseDto> getDirectReplies(Long tweetId) {
        return null;
    }

    public List<TweetResponseDto> getDirectReposts(Long tweetId) {
        return null;
    }

    // public List<UserResponseDto> getUsersMentionedInTweet(Long tweetId){return
    // null;}
}
