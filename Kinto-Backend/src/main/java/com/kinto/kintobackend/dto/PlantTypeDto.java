package com.kinto.kintobackend.dto;

import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
public class PlantTypeDto {

    private String id;
    private String namePlantType;
    private OrganizationDto organization;
    private Boolean status;
    private String code;
    private Instant createAt;
    private Instant updateAt;
}
