package com.cooksys.assessment1.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.cooksys.assessment1.services.HashtagService;
import com.cooksys.assessment1.entities.Hashtag;
import com.cooksys.assessment1.repositories.HashtagRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
	
	private final HashtagRepository hashtagRepository;
	

}
