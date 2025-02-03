package com.hardik.SpringSecurity.SecurityApplication.services;


import com.hardik.SpringSecurity.SecurityApplication.dto.LoginDTO;
import com.hardik.SpringSecurity.SecurityApplication.dto.LoginResponseDTO;
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

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;



    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication=authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
        User user=(User)authentication.getPrincipal();
        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user);
        sessionService.generateNewSession(user,refreshToken);
        return LoginResponseDTO.builder().id(user.getId()).refreshToken(refreshToken).accessToken(accessToken).build();

    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        Long userId=jwtService.getUserIdFromToken(refreshToken);
        sessionService.validateSession(refreshToken);
        User user=userService.getUserById(userId);
        String accessToken= jwtService.generateAccessToken(user);
        return LoginResponseDTO.builder().id(user.getId()).refreshToken(refreshToken).accessToken(accessToken).build();
    }
}
