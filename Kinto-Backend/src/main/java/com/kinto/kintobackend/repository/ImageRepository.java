package com.kinto.kintobackend.repository;

import com.kinto.kintobackend.model.Image;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends MongoRepository<Image, ObjectId> {

    Optional<Image> findByName(String fileName);
    List<Image> findByPlantId(ObjectId id);

}
