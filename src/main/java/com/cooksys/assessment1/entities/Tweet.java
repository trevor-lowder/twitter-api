package com.cooksys.assessment1.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tweet")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @Column(name = "posted")
    @CreationTimestamp
    private Timestamp posted;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "inReplyTo")
    private Tweet inReplyTo;

    @ManyToOne
    @JoinColumn(name = "repostOf")
    private Tweet repostOf;

    @ManyToMany()
    private List<Hashtag> hashtags;

    @ManyToMany(mappedBy = "likedTweets")
    private List<User> likedBy;

    @ManyToMany(mappedBy = "mentionedTweets")
    private List<User> mentionedBy;

}
