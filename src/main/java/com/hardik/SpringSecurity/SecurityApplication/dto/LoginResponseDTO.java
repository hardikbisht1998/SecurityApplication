package com.hardik.SpringSecurity.SecurityApplication.dto;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class LoginResponseDTO {

    private Long id;
    private String accessToken;
    private String refreshToken;
}
