package com.benjamintanone.aboutapi.models;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class SimpleModel {
    @NotNull
    private String text;
}
