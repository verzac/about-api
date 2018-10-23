package com.benjamintanone.aboutapi.repositories;

import com.benjamintanone.aboutapi.models.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SessionRepository extends CrudRepository<Session, String> {
    List<Session> findByContactForm_email(String email);
    List<Session> findByChallenge(String challenge);
}
