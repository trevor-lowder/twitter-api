package com.cooksys.assessment1.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
// import java.util.ArrayList;
// import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tweet")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // @ManyToOne
    // @JoinColumn(name = "author")
    // private User author;

    @Column(name = "posted")
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

    // @ManyToMany
    // @JoinTable(name = "tweet_hashtags",
    // joinColumns = @JoinColumn(name = "tweet_id"),
    // inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    // private List<Hashtag> hashtags = new ArrayList<>();

    // @ManyToMany(mappedBy = "likedTweets")
    // private List<User> likedBy = new ArrayList<>();

    // @ManyToMany(mappedBy = "mentionedTweets")
    // private List<User> mentionedBy = new ArrayList<>();

}
