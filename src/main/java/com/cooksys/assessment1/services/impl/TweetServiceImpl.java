package com.cooksys.assessment1.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.assessment1.dtos.TweetContextDto;
import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.exceptions.NotFoundException;
import com.cooksys.assessment1.repositories.TweetRepository;
import com.cooksys.assessment1.services.TweetService;
import com.cooksys.assessment1.mappers.TweetMapper;
import com.cooksys.assessment1.mappers.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final UserMapper userMapper;

    public TweetResponseDto createTweet(TweetRequestDto TweetRequestDto) {
        return null;
    }

    public List<TweetResponseDto> getAllNonDeletedTweets() {
        List<Tweet> tweets = tweetRepository.findByDeletedFalseOrderByPostedDesc();
        return tweetMapper.entitiesToResponseDtos(tweets);
    }

    public TweetResponseDto getTweetById(Long id) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tweet not found with id: " + id));
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet with id " + id + " is deleted");
        }
        return tweetMapper.entityToResponseDto(tweet);
    }

    public TweetResponseDto deleteTweet(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        if (optionalTweet.isEmpty()) {
            throw new NotFoundException("Tweet not found with ID: " + id);
        }
        Tweet tweet = optionalTweet.get();
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet with ID: " + id + " has already been deleted");
        }
        TweetResponseDto deletedTweet = tweetMapper.entityToResponseDto(tweet);
        tweet.setDeleted(true);
        tweetRepository.save(tweet);
        return deletedTweet;
    }

    public void createLike(Long tweetId, UserRequestDto UserRequestDto) {
    }

    public TweetResponseDto createReply(Long tweetId, TweetRequestDto TweetRequestDto) {
        return null;
    }

    public TweetResponseDto createRepost(Long tweetId, UserRequestDto UserRequestDto) {
        return null;
    }

    public List<String> getTags(Long tweetId) {
        return null;
    }

    public List<UserResponseDto> getLikes(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found with id: " + tweetId));
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet with id " + tweetId + " is deleted");
        }
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
        return null;
    }

    public List<TweetResponseDto> getReplies(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found with id: " + tweetId));
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet with id " + tweetId + " is deleted");
        }
        List<Tweet> replies = tweetRepository.findByInReplyToAndDeletedFalseOrderByPostedDesc(tweet);
        return tweetMapper.entitiesToResponseDtos(replies);
    }

    public List<TweetResponseDto> getReposts(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found with id: " + tweetId));
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet with id " + tweetId + " is deleted");
        }
        List<Tweet> reposts = tweetRepository.findByRepostOfAndDeletedFalseOrderByPostedDesc(tweet);
        List<TweetResponseDto> repostResponseDtos = new ArrayList<>();
        for (Tweet repost : reposts) {
            if (!repost.isDeleted()) {
                repostResponseDtos.add(tweetMapper.entityToResponseDto(repost));
            }
        }
        return repostResponseDtos;
    }

    public List<UserResponseDto> getMentions(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found with id: " + tweetId));
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet with id " + tweetId + " is deleted");
        }
        List<User> mentionedUsers = new ArrayList<>();
        for (User user : tweet.getMentionedBy()) {
            if (!user.isDeleted()) {
                mentionedUsers.add(user);
            }
        }
        return userMapper.entitiesToDtos(mentionedUsers);
    }

}
