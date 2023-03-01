
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
    private UserResponseDto author;
    private Timestamp posted;
    private TweetResponseDto inReplyTo;
    private TweetResponseDto repostOf;
}
