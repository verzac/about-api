package com.benjamintanone.aboutapi.exceptions.handlers;

import com.benjamintanone.aboutapi.exceptions.BadRequestException;
import com.benjamintanone.aboutapi.models.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleResponse handleGeneralException(Exception e) {
        log.error(e.getMessage(), e);
        return SimpleResponse.builder().status(500).message("An unexpected error occurred.").build();
    }
}
