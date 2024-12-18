package com.demo.URL.Shortener.controller;

import com.demo.URL.Shortener.exceptions.URLNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ShortnerExceptionController {

    @ExceptionHandler
    public ResponseEntity<String> exception(URLNotFoundException URLNotFoundException) {
        return new ResponseEntity<>("ShortCode does not exist!", HttpStatus.NOT_FOUND);
    }

}
