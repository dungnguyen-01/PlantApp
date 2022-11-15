package com.kinto.kintobackend.repository;

import com.kinto.kintobackend.model.PlantCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PlantCodeRepository extends MongoRepository<PlantCode, ObjectId> {

    Optional<PlantCode> findByPlantIdAndAndPlantTypeIdAndAndLocationId(ObjectId plantId, ObjectId plantTypeId, ObjectId locationId);
}
