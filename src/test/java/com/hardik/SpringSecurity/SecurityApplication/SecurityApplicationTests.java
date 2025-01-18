package com.hardik.SpringSecurity.SecurityApplication;

import com.hardik.SpringSecurity.SecurityApplication.entities.User;
import com.hardik.SpringSecurity.SecurityApplication.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {

    @Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {
		User user=new User(3L,"hardik@gmail","1233334");
		String token= jwtService.generateToken(user);
		System.out.println(token);
		System.out.println(jwtService.getUserIdFromToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiZW1haWwiOiJoYXJkaWtAZ21haWwiLCJyb2xlcyI6WyJVU0VSIiwiQURNSU4iXSwiaWF0IjoxNzM3MjE5MjkzLCJleHAiOjE3MzcyMTkyOTR9.fJAdqxE2QHK5Dt0R6lTS4ebvSX6GYwucIAtmyrbpQcI"));
	}

}
