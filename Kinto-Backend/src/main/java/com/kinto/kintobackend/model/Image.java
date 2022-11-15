package com.kinto.kintobackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "image")
@TypeAlias(value = "Image")
public class Image {
    @Id
    private ObjectId id;
    private String name;
    private String objectName;
    private String bucketName;
    private ObjectId plantId;
    private ObjectId orgId;
    private Instant createAt = Instant.now();
    private Instant updateAt;


}
