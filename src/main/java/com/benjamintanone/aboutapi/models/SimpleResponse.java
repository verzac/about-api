package com.benjamintanone.aboutapi.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleResponse {
    private int status;
    private String message;
}
