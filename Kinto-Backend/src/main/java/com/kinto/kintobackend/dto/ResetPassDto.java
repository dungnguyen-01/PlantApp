package com.kinto.kintobackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ResetPassDto {
    private String token;
    private String email;
    private String password;
    private String confirm;

}
