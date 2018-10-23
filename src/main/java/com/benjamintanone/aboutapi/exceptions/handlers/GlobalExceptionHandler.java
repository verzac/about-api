package com.benjamintanone.aboutapi.exceptions.handlers;

import com.benjamintanone.aboutapi.exceptions.BadRequestException;
import com.benjamintanone.aboutapi.models.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleResponse handleBadRequestException(Exception exception) {
        log.error(exception.getMessage());
        return SimpleResponse.builder()
                .message(exception.getMessage())
                .status(400)
                .build();
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleResponse handleGeneralException(Exception e) {
        log.error(e.getMessage());
        return SimpleResponse.builder().status(500).message("An unexpected error occurred.").build();
    }
}
