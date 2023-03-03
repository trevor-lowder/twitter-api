package com.cooksys.assessment1.services;

 import java.util.List;
 import com.cooksys.assessment1.dtos.HashtagDto;
import com.cooksys.assessment1.entities.Hashtag;

public interface HashtagService {

	List<HashtagDto> getHashtags();
	
	

}
