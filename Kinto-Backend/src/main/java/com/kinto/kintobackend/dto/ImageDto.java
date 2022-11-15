package com.kinto.kintobackend.dto;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.Instant;

@Data
public class ImageDto {

    private String id;
    private String name;
    private String objectName;
    private String bucketName;
    private ObjectId plantId;
    private OrganizationDto organization;
    private Instant createAt = Instant.now();
    private Instant updateAt;
}
