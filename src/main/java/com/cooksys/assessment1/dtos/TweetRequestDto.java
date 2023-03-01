
package com.cooksys.assessment1.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TweetRequestDto {
    private String content;
    private Long inReplyToId;
    private Long repostOfId;
    private List<Long> hashtagIds;
}
