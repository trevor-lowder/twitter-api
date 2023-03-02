package com.cooksys.assessment1.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
	
	//@Mapping(target="profileDto", source="profile")
	@Mapping(target="username", source="credentials.username")
	UserResponseDto entityToDto(User entity);

//	@Mapping(target="profile", source="profileDto")
//	@Mapping(target="credentials", source="credentialsDto")
	User requestDtoToEntity(UserRequestDto userRequestDto);
	
	List<UserResponseDto> entitiesToDtos(List<User> entities);

	List<User> requestDtosToEntities(List<UserRequestDto> userRequestDtos);
}
