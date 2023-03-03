package com.cooksys.assessment1.mappers;

import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.TweetContextDto;
import com.cooksys.assessment1.entities.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TweetMapper {

    Tweet requestDtoToEntity(TweetRequestDto tweetRequestDto);

    TweetResponseDto entityToResponseDto(Tweet tweet);

    TweetContextDto entityToContextDto(Tweet target, List<Tweet> before, List<Tweet> after);

    List<TweetResponseDto> entitiesToResponseDtos(List<Tweet> tweets);

    List<Tweet> requestDtosToEntities(List<TweetRequestDto> tweetRequestDtos);

}
