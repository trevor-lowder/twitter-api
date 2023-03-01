package com.cooksys.assessment1.controllers.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cooksys.assessment1.dtos.ErrorDto;
import com.cooksys.assessment1.exceptions.BadRequestException;

@ControllerAdvice(basePackages = {"com.cooksys.assessment1.controllers"})
@ResponseBody
public class Assessment1ControllerAdvice {
	
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDto handleBadREquestException(HttpServletRequest request, BadRequestException badRequestException) {
		return new ErrorDto(badRequestException.getMessage());
	}

}
