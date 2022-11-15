package com.kinto.kintobackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "plant")
public class Plant {

    @Id
    private ObjectId id;
    @NotEmpty(message = "cultureMedium cannot be null")
    private String cultureMedium;
    private ObjectId locationId;
    private ObjectId picId;
    private ObjectId plantTypeId;
    private ObjectId orgId;
    private String code;
    private Instant createAt;
    private Instant updateAt;
}
