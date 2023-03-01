package com.cooksys.assessment1.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import com.cooksys.assessment1.entities.Hashtag;
import com.cooksys.assessment1.dtos.HashtagDto;


@Mapper(componentModel = "spring")
public interface HashtagMapper{
	
	 HashtagDto entityToDto(Hashtag entity);

	 List<HashtagDto> entitiesToDtos(List<Hashtag> entities);

	
}
	

