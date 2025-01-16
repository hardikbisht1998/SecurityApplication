package com.hardik.SpringSecurity.SecurityApplication.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    private Long id;

    private String title;

    private String description;
}
