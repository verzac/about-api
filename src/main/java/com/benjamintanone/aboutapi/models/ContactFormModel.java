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

    public String toString() {
        return String.format("EMAIL:\n%s\n\nFIRST NAME:\n%s\n\nLAST NAME:\n%s\n\nMESSAGE:\n%s\n\nREASON:\n%s\n",
                this.email, this.firstName, this.lastName, this.message, this.reason);
    }
}
