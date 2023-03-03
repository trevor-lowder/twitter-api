package com.cooksys.assessment1.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.cooksys.assessment1.services.HashtagService;
import com.cooksys.assessment1.dtos.HashtagDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.entities.Hashtag;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.mappers.HashtagMapper;
import com.cooksys.assessment1.mappers.TweetMapper;
import com.cooksys.assessment1.repositories.HashtagRepository;
import com.cooksys.assessment1.repositories.TweetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
	
	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;
	private final TweetRepository tweetRepository;
	private final TweetMapper tweetMapper;

	@Override
	public List<HashtagDto> getHashtags() {
		return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
		
	}

	@Override
	public List<TweetResponseDto> getTweetFromLabel(String label) {
		
		Hashtag h = hashtagRepository.findByLabel(label);
		
		if(h.equals(null)) {
			//error message
		}
		
		
		return tweetMapper.entitiesToResponseDtos(h.getTweets());
	}
	

}
