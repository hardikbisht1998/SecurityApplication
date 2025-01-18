package com.hardik.SpringSecurity.SecurityApplication.controllers;


import com.hardik.SpringSecurity.SecurityApplication.dto.LoginDTO;
import com.hardik.SpringSecurity.SecurityApplication.dto.SignUpDTO;
import com.hardik.SpringSecurity.SecurityApplication.dto.UserDTO;
import com.hardik.SpringSecurity.SecurityApplication.services.AuthService;
import com.hardik.SpringSecurity.SecurityApplication.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){
            return new ResponseEntity<>(userService.signUp(signUpDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String token=authService.login(loginDTO);

        Cookie cookie=new Cookie("token",token);
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok(token);

    }
}
