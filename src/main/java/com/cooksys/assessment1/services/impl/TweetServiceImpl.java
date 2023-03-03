package com.cooksys.assessment1.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.assessment1.dtos.HashtagDto;
import com.cooksys.assessment1.dtos.TweetContextDto;
import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.exceptions.BadRequestException;
import com.cooksys.assessment1.exceptions.NotFoundException;
import com.cooksys.assessment1.repositories.TweetRepository;
import com.cooksys.assessment1.repositories.UserRepository;
import com.cooksys.assessment1.services.TweetService;
import com.cooksys.assessment1.mappers.HashtagMapper;
import com.cooksys.assessment1.mappers.TweetMapper;
import com.cooksys.assessment1.mappers.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final UserMapper userMapper;
    private final HashtagMapper hashtagMapper;
    private final UserRepository userRepository;

    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {

        if (tweetRequestDto.getCredentials() == null || tweetRequestDto.getCredentials().getUsername() == null
                || tweetRequestDto.getCredentials().getPassword() == null
                || tweetRequestDto.getContent() == null) {
            throw new BadRequestException("Username, password and tweet content are required.");
        }

        Optional<User> user = userRepository
                .findByCredentialsUsernameAndDeletedFalse(tweetRequestDto.getCredentials().getUsername());
        if (user.isEmpty()) {
            throw new NotFoundException(
                    "No user found by username '" + tweetRequestDto.getCredentials().getUsername() + "'");
        }

        Tweet tweet = tweetMapper.requestDtoToEntity(tweetRequestDto);
        tweet.setAuthor(user.get());

        // FIXME responseDto is returning a null username, all other fields are correct
        // TODO Add parsing methods to find mentions and hashtags in tweet content.

        return tweetMapper.entityToResponseDto(tweetRepository.saveAndFlush(tweet));
    }

    public List<TweetResponseDto> getAllNonDeletedTweets() {
        List<Tweet> tweets = tweetRepository.findByDeletedFalseOrderByPostedDesc();
        return tweetMapper.entitiesToResponseDtos(tweets);
    }

    public TweetResponseDto getTweetById(Long tweetId) {
        Tweet tweet = getValidTweet(tweetId);
        return tweetMapper.entityToResponseDto(tweet);
    }

    public TweetResponseDto deleteTweet(Long tweetId) {
        Tweet tweet = getValidTweet(tweetId);
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

    public List<UserResponseDto> getLikes(Long tweetId) {
        Tweet tweet = getValidTweet(tweetId);
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
        Tweet tweet = getValidTweet(tweetId);
        List<Tweet> replies = tweetRepository.findByInReplyToAndDeletedFalseOrderByPostedDesc(tweet);
        return tweetMapper.entitiesToResponseDtos(replies);
    }

    public List<TweetResponseDto> getReposts(Long tweetId) {
        Tweet tweet = getValidTweet(tweetId);
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
        Tweet tweet = getValidTweet(tweetId);
        List<User> mentionedUsers = tweet.getMentionedBy();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : mentionedUsers) {
            if (!user.isDeleted()) {
                userResponseDtos.add(userMapper.entityToDto(user));
            }
        }
        return userResponseDtos;
    }

    @Override
    public List<HashtagDto> getHashtagsFromTweetId(Long id) {
        Tweet tweet = getValidTweet(id);
        return hashtagMapper.entitiesToDtos(tweet.getHashtags());
    }

    private Tweet getValidTweet(Long tweetId) throws NotFoundException {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found with id: " + tweetId));
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet with id " + tweetId + " is deleted");
        }
        return tweet;
    }

}
