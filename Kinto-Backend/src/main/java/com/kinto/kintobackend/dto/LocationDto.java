package com.kinto.kintobackend.dto;

import lombok.Data;
import org.bson.types.ObjectId;
import java.time.Instant;

@Data
public class LocationDto {

    private String id;
    private String address;
    private OrganizationDto organization;
    private Boolean status;
    private String code;
    private Instant createAt;
    private Instant updateAt;
}
