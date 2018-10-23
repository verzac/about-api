package com.benjamintanone.aboutapi.exceptions;


public class BadRequestException extends RuntimeException {
    public BadRequestException(String msg) {
        super(msg);
    }
}
