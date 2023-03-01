package com.cooksys.assessment1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetContextDto {
    private TweetResponseDto target;
    private TweetResponseDto[] before;
    private TweetResponseDto[] after;
}
