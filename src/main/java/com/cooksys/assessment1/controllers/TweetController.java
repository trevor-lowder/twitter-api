package com.cooksys.assessment1.controllers;

import com.cooksys.assessment1.dtos.TweetRequestDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
// import com.cooksys.assessment1.services.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetController {

    // private final TweetService tweetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        // TODO: Implement this method
        return null;
    }

    @GetMapping
    public List<TweetResponseDto> getAllTweets() {
        // TODO: Implement this method
        return null;
    }

    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable Long id) {
        // TODO: Implement this method
        return null;
    }

    @PatchMapping("/{id}")
    public TweetResponseDto updateTweet(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
        // TODO: Implement this method
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTweet(@PathVariable Long id) {
        // TODO: Implement this method
    }
}
