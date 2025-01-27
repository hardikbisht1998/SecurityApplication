package com.hardik.SpringSecurity.SecurityApplication.controllers;


import com.hardik.SpringSecurity.SecurityApplication.dto.LoginDTO;
import com.hardik.SpringSecurity.SecurityApplication.dto.LoginResponseDTO;
import com.hardik.SpringSecurity.SecurityApplication.dto.SignUpDTO;
import com.hardik.SpringSecurity.SecurityApplication.dto.UserDTO;
import com.hardik.SpringSecurity.SecurityApplication.services.AuthService;
import com.hardik.SpringSecurity.SecurityApplication.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Value("${deploy.env}")
    private String deployEnv;

    @Autowired
    private AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){
            return new ResponseEntity<>(userService.signUp(signUpDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        LoginResponseDTO loginResponseDTO=authService.login(loginDTO);

        Cookie cookie=new Cookie("refreshToken",loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDTO);

    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){
        String refreshToken=Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()->new AuthorizationServiceException("Refresh token not found inside the cookie"));
        LoginResponseDTO loginResponseDTO=authService.refreshToken(refreshToken);
        return ResponseEntity.ok(loginResponseDTO);

    }
}
