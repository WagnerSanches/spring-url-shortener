package com.demo.URL.Shortener.controller;

import com.demo.URL.Shortener.dto.ErrorResponseDTO;
import com.demo.URL.Shortener.exception.URLNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ShortnerExceptionController {

    Logger logger = LoggerFactory.getLogger(ShortnerExceptionController.class);

    @ExceptionHandler(URLNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> urlNotFoundException(URLNotFoundException URLNotFoundException) {
        logger.error("URLNotFoundException: ShortCode does not exist!");
        return new ResponseEntity<>(ErrorResponseDTO.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status("NOT FOUND")
                .message("ShortCode does not exist")
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> exception(Exception exception) {
        logger.error(exception.getMessage());
        return new ResponseEntity<>(ErrorResponseDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status("INTERNAL SERVER ERROR")
                .timestamp(LocalDateTime.now())
                .stacktrace(exception.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
