package com.cooksys.assessment1.dtos;

import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HashtagDto {

	@Id
	@GeneratedValue
	private Long id;

	private String label;

	private Timestamp firstUsed;
	private Timestamp lastUsed;

}
