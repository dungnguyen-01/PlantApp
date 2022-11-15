package com.kinto.kintobackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "user")
public class User {
    @Id
    private ObjectId id;
    @NotEmpty(message = "fullname cannot be null")
    private String fullname;
    private String password;

    @Email(message = "Incorrect email format")
    @NotEmpty(message = "email cannot be null")
    private String email;
    private ObjectId orgId;
    private ObjectId roleId;
    private Boolean status;
    private Instant createAt;
    private Instant updateAt;

    //role -> rid, rname, list<permission> EX-> read_plant, update_plant, delete_plant

}
