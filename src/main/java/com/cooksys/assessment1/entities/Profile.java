package com.cooksys.assessment1.entities;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Profile class contains fields that are the profile section of a User.
 * This class is embedded into a User object.
 * 
 * @author Scott VanBrunt
 *
 */
@NoArgsConstructor
@Data
@Embeddable
public class Profile {

	private String firstName;

	private String lastName;

	private String email;

	private String phone;
}
