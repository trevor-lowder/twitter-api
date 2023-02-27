package com.cooksys.assessment1.mappers;

import org.mapstruct.Mapper;

import com.cooksys.assessment1.dtos.ProfileDto;
import com.cooksys.assessment1.entities.Profile;

/**
 * ProfileMapper interface is a mapper used for Spring Boot code generation. For
 * mapping Profile objects to and from a DTO.
 * 
 * @author Scott VanBrunt
 *
 */
@Mapper(componentModel = "spring")
public interface ProfileMapper {

	ProfileDto entityToDto(Profile entity);

	Profile dtoToEntity(ProfileDto profileDto);

}
