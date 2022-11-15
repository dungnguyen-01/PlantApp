package com.kinto.kintobackend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "location")
@TypeAlias(value = "Location")
public class Location {

    @Id
    private ObjectId id;

    @NotEmpty(message = "address cannot be null")
    private String address;
    private ObjectId orgId;
    private Boolean status;

    private String code;
    private Instant createAt;
    private Instant updateAt;
}
