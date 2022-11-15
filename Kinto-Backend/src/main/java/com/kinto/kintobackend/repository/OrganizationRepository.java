package com.kinto.kintobackend.repository;

import com.kinto.kintobackend.model.Organization;
import com.kinto.kintobackend.model.PlantType;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends MongoRepository<Organization, ObjectId> {

    @Query("{'name' : ?0}")
    Optional<Organization> findByName(String name);

    List<Organization> findByIdIn(List<ObjectId> organizationIds);

    Optional<Organization> findByIdAndStatusIsTrue(ObjectId id);

    List<Organization> findAllByNameIsNotLikeAndStatusIsTrue(String name);

    List<Organization> findAllByStatusIsTrue();

    Page<Organization> findAllByStatusIsTrue(Pageable pageable);
}
