package com.kinto.kintobackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "plantType")
@TypeAlias(value = "PlantType")
public class PlantType {

    @Id
    private ObjectId id;
    @NotEmpty(message = "namePlantType cannot be null")
    private String namePlantType;
    private ObjectId orgId;
    private Boolean status;
    private String code;
    private Instant createAt;
    private Instant updateAt;

}
