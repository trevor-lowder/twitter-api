package com.cooksys.assessment1.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.assessment1.dtos.CredentialsDto;
import com.cooksys.assessment1.dtos.HashtagDto;
import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.services.TweetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetController {

	private final TweetService tweetService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.createTweet(tweetRequestDto);
	}

	@GetMapping
	public List<TweetResponseDto> getAllNonDeletedTweets() {
		return tweetService.getAllNonDeletedTweets();
	}

	@GetMapping("/{id}")
	public TweetResponseDto getTweetById(@PathVariable Long id) {
		TweetResponseDto tweetResponseDto = tweetService.getTweetById(id);
		return tweetResponseDto;
	}

	@GetMapping("/{id}/likes")
	public List<UserResponseDto> getLikes(@PathVariable() Long id) {
		return tweetService.getLikes(id);
	}

	@GetMapping("/{id}/replies")
	public List<TweetResponseDto> getReplies(@PathVariable Long id) {
		return tweetService.getReplies(id);
	}

	@GetMapping("/tweets/{id}/mentions")
	public List<UserResponseDto> getMentions(@PathVariable Long id) {
		return tweetService.getMentions(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
		TweetResponseDto tweetResponseDto = tweetService.deleteTweet(id, credentialsDto);
		return tweetResponseDto;
	}

	@GetMapping("/{id}/tags")
	public List<HashtagDto> getHashtagsFromTweetId(@PathVariable Long id) {
		return tweetService.getHashtagsFromTweetId(id);		
	}
	
	@PostMapping("/{id}/like")
	public void likeTweet (@RequestBody CredentialsDto credentialsDto, @PathVariable Long id) {
		tweetService.createLike(id, credentialsDto);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/{id}/reply")
	public TweetResponseDto createReply (@RequestBody TweetRequestDto tweetRequestDto, @PathVariable Long id) {
		return tweetService.createReply(id, tweetRequestDto);
	}
	
	@GetMapping("/{id}/reposts")
	public List<TweetResponseDto> getReposts(@PathVariable Long id){
		return tweetService.getReposts(id);
	}
	
	@PostMapping("/{id}/repost")
	@ResponseStatus(HttpStatus.CREATED)
	public TweetResponseDto createRepost(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
		return tweetService.createRepost(id, credentialsDto);
	}
}
