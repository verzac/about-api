package com.benjamintanone.aboutapi.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@Data
@RedisHash(value = "sessions", timeToLive = 86400) // 86400 - 1 day to expire
@Builder
public class Session {
    @Id
    private String id;

    @Indexed
    private String challenge;

    private ContactForm contactForm;
}
