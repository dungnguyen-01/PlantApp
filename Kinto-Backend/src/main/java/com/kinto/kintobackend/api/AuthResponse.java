package com.kinto.kintobackend.api;

import com.kinto.kintobackend.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String email;
    private String role;
    private String accessToken;
    private UserDto user;
}
