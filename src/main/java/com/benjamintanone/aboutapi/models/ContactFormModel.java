package com.benjamintanone.aboutapi.models;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ContactFormModel {
    @NotNull
    private String email;
    @NotNull
    private String message;
    @NotNull
    private String reason;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
