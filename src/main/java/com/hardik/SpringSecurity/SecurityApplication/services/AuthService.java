package com.hardik.SpringSecurity.SecurityApplication.services;


import com.hardik.SpringSecurity.SecurityApplication.dto.LoginDTO;
import com.hardik.SpringSecurity.SecurityApplication.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public String login(LoginDTO loginDTO) {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
        User user=(User)authentication.getPrincipal();
        return jwtService.generateToken(user);
    }
}
