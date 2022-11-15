package com.kinto.kintobackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "PlantCode")
@TypeAlias(value = "PlantCode")
public class PlantCode {

    @Id
    private ObjectId id;
    private ObjectId plantTypeId;
    private ObjectId locationId;
    private ObjectId plantId;

}
