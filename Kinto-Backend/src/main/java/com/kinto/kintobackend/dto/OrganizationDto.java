package com.kinto.kintobackend.dto;

import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
public class OrganizationDto {

    private String id;
    private String name;
    private Boolean status;
    private Instant createAt;
    private Instant updateAt;
}
