package com.kinto.kintobackend.dto;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.Instant;

@Data
public class FarmDiaryDto {

    private String id;
    private String name;
    private OrganizationDto organization;
    private ObjectId plantId;
    private Instant createAt;
    private Instant updateAt;
}
