package com.cooksys.assessment1.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.User;

/**
 * UserMapper interface is a mapper used for Spring Boot code generation. For
 * mapping User objects to and from RequestDtos and ResponseDtos.
 * 
 * @author Scott VanBrunt
 *
 */
@Mapper(componentModel = "spring", uses = { ProfileMapper.class, CredentialsMapper.class })
public interface UserMapper {
	
	UserResponseDto entityToDto(User entity);

	List<UserResponseDto> entitiesToDtos(List<User> entities);

	User requestDtoToEntity(UserRequestDto userRequestDto);

	List<User> requestDtosToEntities(List<UserRequestDto> userRequestDtos);
}
