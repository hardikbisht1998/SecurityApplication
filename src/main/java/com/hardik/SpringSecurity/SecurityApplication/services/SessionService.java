package com.hardik.SpringSecurity.SecurityApplication.services;

import com.hardik.SpringSecurity.SecurityApplication.entities.Session;
import com.hardik.SpringSecurity.SecurityApplication.entities.User;
import com.hardik.SpringSecurity.SecurityApplication.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    private final int SESSION_LIMIT=2;

    public void generateNewSession(User user, String refreshToken){
        List<Session> userSession =sessionRepository.findByUser(user);
        if(userSession.size()==SESSION_LIMIT){
            userSession.sort(Comparator.comparing(Session::getLastUsedAt));
            Session leastRecentlyUsedSession=userSession.get(0);
            sessionRepository.delete(leastRecentlyUsedSession);
        }

        Session newSession=Session
                .builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

        sessionRepository.save(newSession);

    }

    public  void validateSession(String refreshToken){
        Session session=sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new SessionAuthenticationException("Session not found for refresh token"+refreshToken));

        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);


    }

}
