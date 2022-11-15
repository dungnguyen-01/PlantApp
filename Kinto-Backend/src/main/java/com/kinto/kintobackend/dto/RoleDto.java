package com.kinto.kintobackend.dto;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.List;

@Data
public class RoleDto {

    private String id;
    private String roleName;
    private Instant createAt = Instant.now();
    private Instant updateAt;
}
