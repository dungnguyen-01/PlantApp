package com.kinto.kintobackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "farmDiary")
@TypeAlias(value = "FarmDiary")
public class FarmDiary {

    @Id
    private ObjectId id;
    private String name;
    private ObjectId plantId;
    private ObjectId orgId;
    private Instant createAt = Instant.now();
    private Instant updateAt;
}
