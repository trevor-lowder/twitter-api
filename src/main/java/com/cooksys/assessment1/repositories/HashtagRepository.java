package com.cooksys.assessment1.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cooksys.assessment1.entities.Hashtag;



public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
	
	Hashtag findByLabel(String label);

	List<Hashtag> findAllByLabel(String label);

}
