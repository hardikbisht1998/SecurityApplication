package com.hardik.SpringSecurity.SecurityApplication.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

    private Long id;
    private String accessToken;
    private String refreshToken;
}
