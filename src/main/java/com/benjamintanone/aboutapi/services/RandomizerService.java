package com.benjamintanone.aboutapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class RandomizerService {
    private SecureRandom random;

    @Autowired
    public RandomizerService() throws NoSuchAlgorithmException {
        this.random = SecureRandom.getInstanceStrong();
    }

    public String getNewChallengeToken() {
        byte bytes[] = new byte[24];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }
}
