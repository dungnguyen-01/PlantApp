package com.kinto.kintobackend.repository;


import com.kinto.kintobackend.model.PlantType;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PlantTypeRepository extends MongoRepository<PlantType, ObjectId> {
    Optional<PlantType> findByNamePlantType(String namePlantType);

    List<PlantType> findByIdIn(List<ObjectId> plantTypeIds);

    List<PlantType> findByStatusIsTrueAndOrgIdEquals(ObjectId orgId);

    boolean existsByCode(String code);

    Page<PlantType> findAllByStatusIsTrueAndOrgIdEquals(ObjectId orgId, Pageable pageable);

    Optional<PlantType> findByCode(String code);
}
