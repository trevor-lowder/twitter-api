package com.cooksys.assessment1.entities;

import java.io.Serializable;

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
public class Profile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7197642447942331842L;

	private String firstName;

	private String lastName;

	private String email;

	private String phone;
}
