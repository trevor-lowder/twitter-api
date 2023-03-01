
package com.cooksys.assessment1.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
// import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TweetResponseDto {
    private Long id;
    private String content;
    // private UserResponseDto author;
    private Timestamp posted;
    private boolean deleted;
    private TweetResponseDto inReplyTo;
    private TweetResponseDto repostOf;
    // private List<HashtagResponseDto> hashtags;
    // private List<UserResponseDto> mentionedBy;
    // private List<UserResponseDto> likedBy;
}
