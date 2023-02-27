package com.cooksys.assessment1.mappers;

import org.mapstruct.Mapper;

import com.cooksys.assessment1.dtos.CredentialsDto;
import com.cooksys.assessment1.entities.Credentials;

/**
 * CredentialsMapper interface is a mapper used for Spring Boot code generation.
 * For mapping Credentials objects to and from a DTO.
 * 
 * @author Scott VanBrunt
 *
 */
@Mapper(componentModel = "spring")
public interface CredentialsMapper {

	CredentialsDto entityToDto(Credentials entity);

	Credentials dtoToEntity(CredentialsDto credentialsDto);
}
