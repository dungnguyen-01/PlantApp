package com.kinto.kintobackend.repository;

import com.kinto.kintobackend.model.Plant;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PlantRepository extends MongoRepository<Plant, ObjectId> {

    Page<Plant> findByOrgIdEquals(ObjectId orgId, Pageable pageable);

    Page<Plant> findByOrgIdEqualsAndPlantTypeIdAndLocationIdAndPicId(ObjectId orgId, ObjectId plantTypeId, ObjectId locationId, ObjectId picId, Pageable pageable);

    Page<Plant> findByOrgIdEqualsAndPicId(ObjectId orgId,ObjectId picId, Pageable pageable);

    Page<Plant> findByOrgIdEqualsAndPlantTypeIdAndPicId(ObjectId orgId,ObjectId plantTypeId, ObjectId picId, Pageable pageable);

    Page<Plant> findByOrgIdEqualsAndPlantTypeIdAndLocationId(ObjectId orgId,ObjectId plantTypeId, ObjectId locationId, Pageable pageable);

    Page<Plant> findByOrgIdEqualsAndPicIdAndLocationId(ObjectId orgId,ObjectId picId, ObjectId locationId, Pageable pageable);

    Page<Plant> findByOrgIdEqualsAndLocationId(ObjectId orgId,ObjectId locationId, Pageable pageable);

    Page<Plant> findByOrgIdEqualsAndPlantTypeId(ObjectId orgId,ObjectId plantTypeId, Pageable pageable);

    boolean existsByCode(String code);
}
