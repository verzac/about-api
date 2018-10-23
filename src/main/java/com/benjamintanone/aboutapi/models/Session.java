package com.benjamintanone.aboutapi.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@Data
@RedisHash("sessions")
@Builder
public class Session {
    @Id
    private String id;

    @Indexed
    private String challenge;

    private ContactForm contactForm;
}
