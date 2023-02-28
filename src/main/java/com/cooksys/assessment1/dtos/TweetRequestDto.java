package com.cooksys.assessment1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TweetRequestDto {

    private String content;

    private Long inReplyTo;

    private Long repostOf;
}
