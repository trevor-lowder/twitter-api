package com.cooksys.assessment1.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.assessment1.services.ValidateService;

import lombok.RequiredArgsConstructor;

/**
 * The ValidateController class represents the end-point interacted with from
 * outside the application. Maps functions further within the application to
 * end-point urls.
 * 
 * All requests are sent to the UserService interface for handling.
 * 
 * @author Scott VanBrunt
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

	private final ValidateService validateService;
}
