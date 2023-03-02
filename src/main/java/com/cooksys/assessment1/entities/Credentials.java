package com.cooksys.assessment1.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Credentials class contains fields that are the login section of a User.
 * This class is embedded into a User object.
 * 
 * @author Scott VanBrunt
 *
 */
@NoArgsConstructor
@Data
@Embeddable
public class Credentials implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -223701857395912542L;

	@Column(unique=true)
	private String username;

	private String password;
}
