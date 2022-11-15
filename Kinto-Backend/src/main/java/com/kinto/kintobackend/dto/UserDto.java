package com.kinto.kintobackend.dto;


import lombok.Data;
import java.time.Instant;


@Data
public class UserDto {

    private String id;
    private String fullname;
    private String password;
    private String email;
    private OrganizationDto organization;
    private RoleDto role;
    private Boolean status;
    private Instant createAt;
    private Instant updateAt;


}
