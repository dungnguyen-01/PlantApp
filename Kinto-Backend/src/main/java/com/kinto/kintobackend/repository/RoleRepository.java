package com.kinto.kintobackend.repository;

import com.kinto.kintobackend.model.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, ObjectId> {
    Optional<Role> findByRoleName (String roleName);
    @Query("{'id' : ?0}")
    Role getById(ObjectId id);
    List<Role> findByIdIn(List<ObjectId> roleIds);
}
