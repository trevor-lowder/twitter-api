package com.cooksys.assessment1.services.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.cooksys.assessment1.services.HashtagService;
import com.cooksys.assessment1.dtos.HashtagDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.entities.Hashtag;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.exceptions.NotFoundException;
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
			throw new NotFoundException("No tweet found by label '" + label + "'");
		}
		
		List<Tweet> t = h.getTweets();
		
		Collections.sort(t, Collections.reverseOrder(Comparator.comparing(Tweet::getPosted)));
		return tweetMapper.entitiesToResponseDtos(t);
	}
	

}
