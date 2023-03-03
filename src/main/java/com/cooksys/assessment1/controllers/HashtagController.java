package com.cooksys.assessment1.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.assessment1.dtos.HashtagDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.entities.Hashtag;
import com.cooksys.assessment1.mappers.HashtagMapper;
import com.cooksys.assessment1.services.HashtagService;

// import com.cooksys.assessment1.services.HashtagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {

	 private final HashtagService hashtagService;
	 
	 // GET ALL HASHTAGS
	 @GetMapping
	 public List<HashtagDto> GetHashtags() {
		return hashtagService.getHashtags();	 
	 }
	 
	 // GET TWEETS FROM LABEL
	 @GetMapping("/@{label}")
	 public List<TweetResponseDto> GetTweetFromLabel(@PathVariable String label){
		 return hashtagService.getTweetFromLabel(label);
	 }

}
