package com.kinto.kintobackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "organization")
public class Organization {
    @Id
    private ObjectId id;

    @NotEmpty(message = "name cannot be null")
    @Indexed(unique = true)
    private String name;
    private Boolean status;
    private Instant createAt;
    private Instant updateAt;

}
