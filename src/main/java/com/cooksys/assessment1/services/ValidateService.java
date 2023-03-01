package com.cooksys.assessment1.services;

/**
 * The ValidateService interface is an intermediate interface used to allow simple update/replacement of
 * the implementing class.
 * 
 * @author Scott VanBrunt
 *
 */
public interface ValidateService {

	/**
	 * 
	 * @param userName String
	 * @return Boolean of true is username exists, false otherwise
	 */
	Boolean ifUserNameExists(String userName);

	/**
	 * 
	 * @param userName String
	 * @return Boolean of true is username is available, false otherwise
	 */
	Boolean ifUserNameAvailable(String userName);

}
