package com.cooksys.assessment1.entities;

import java.util.List;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {

  @Id
  @GeneratedValue
  private Long id;

  private String label;
  
  private Timestamp firstUsed;
  private Timestamp lastUsed;
  
  @OneToMany(mappedBy = "hashtag_id")
  private Integer tweet_hashtags;


//
}
