package com.cooksys.assessment1.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

import java.util.List;

// import com.cooksys.assesment1.entities.User

@Data
@NoArgsConstructor
@Entity
@Table(name = "tweet")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // TODO remove comments once User object is added
    /*
     * @ManyToOne(fetch = FetchType.LAZY)
     * 
     * @JoinColumn(name = "author")
     * 
     * @EqualsAndHashCode.Exclude
     * private User author;
     */

    @Column(name = "posted")
    @CreationTimestamp
    private Timestamp posted;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "id")
    @EqualsAndHashCode.Exclude
    private Tweet inReplyTo;

    @ManyToOne
    @JoinColumn(name = "repostOf")
    @EqualsAndHashCode.Exclude
    private Tweet repostOf;

    @ManyToMany(mappedBy = "likedTweets")
    private List<Tweet> likes;

    @ManyToMany(mappedBy = "mentionedTweets")
    private List<Tweet> mentions;

}
