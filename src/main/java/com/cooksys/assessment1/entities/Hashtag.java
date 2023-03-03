package com.cooksys.assessment1.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {

  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true)
  private String label;
  
  @CreationTimestamp
  private Timestamp firstUsed;

  private Timestamp lastUsed;

  @ManyToMany(mappedBy = "hashtags")
  private List<Tweet> tweets;

}
