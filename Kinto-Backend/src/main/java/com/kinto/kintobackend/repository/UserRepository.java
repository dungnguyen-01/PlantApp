package com.kinto.kintobackend.repository;

import com.kinto.kintobackend.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByEmailAndStatusIsTrue(String email);

    Optional<User> findByIdAndStatusIsTrue(ObjectId id);

    Boolean existsByEmail(String email);

    List<User> findByIdIn(List<ObjectId> userIds);

    List<User> findByStatusIsTrueAndOrgIdEquals(ObjectId id);

    Page<User> findAllByStatusIsTrue(Pageable pageable);
}
