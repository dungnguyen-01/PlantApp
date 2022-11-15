package com.kinto.kintobackend.repository;

import com.kinto.kintobackend.model.Location;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends MongoRepository<Location, ObjectId> {
    Optional<Location> findByAddress(String address);

    List<Location> findByIdIn(List<ObjectId> locationIds);

    List<Location> findByStatusIsTrueAndOrgIdEquals(ObjectId orgId);

    boolean existsByCode(String code);

    Page<Location> findAllByStatusIsTrueAndOrgIdEquals(ObjectId orgId, Pageable pageable);


    Optional<Location> findByCode(String code);
}
