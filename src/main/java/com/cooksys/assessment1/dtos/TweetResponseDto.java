package com.cooksys.assessment1.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TweetResponseDto {

    @JsonProperty("tweet_id")
    private Long id;

    @JsonProperty("content")
    private String content;

    // TODO import UserResponseDto
    /*
     * @JsonProperty("tweet_author")
     * private UserResponseDto author;
     */

    @JsonProperty("posted")
    private LocalDateTime posted;

    @JsonProperty("in_reply_to")
    private TweetResponseDto inReplyTo;

    @JsonProperty("repost_of")
    private TweetResponseDto repostOf;
}
