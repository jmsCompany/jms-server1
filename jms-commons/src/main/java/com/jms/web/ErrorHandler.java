package com.jms.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


//@ControllerAdvice
public class ErrorHandler {

//@ExceptionHandler(value = Exception.class)
//@ResponseBody
public ErrorResponse errorResponse(Exception exception) {
	System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEe");
    return new ErrorResponse(exception.getMessage());
}

}