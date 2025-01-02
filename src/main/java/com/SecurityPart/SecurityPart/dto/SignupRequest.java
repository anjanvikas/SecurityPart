package com.SecurityPart.SecurityPart.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    
    @NotBlank
    @Size(max = 20)
    private String userName;

    @NotBlank
    @Size(min = 6, max = 40)
    private String passWord;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;
}
