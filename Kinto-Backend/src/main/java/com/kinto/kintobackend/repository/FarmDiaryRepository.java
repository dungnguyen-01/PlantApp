package com.kinto.kintobackend.repository;

import com.kinto.kintobackend.model.FarmDiary;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FarmDiaryRepository extends MongoRepository<FarmDiary, ObjectId> {

    List<FarmDiary> findByIdIn(List<ObjectId> farmDiaryId);
    List<FarmDiary> findByPlantId(ObjectId id);
}
