package com.SecurityPart.SecurityPart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    
    @NotBlank
    private String userNameorEmail;

    @NotBlank
    @Size(min = 6, max = 40)
    private String passWord;
}
