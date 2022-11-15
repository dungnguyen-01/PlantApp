package com.kinto.kintobackend.service;

import com.kinto.kintobackend.dto.FarmDiaryDto;
import com.kinto.kintobackend.model.FarmDiary;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public interface FarmDiaryService {
    FarmDiaryDto getById(ObjectId farmDiaryId);

    List<FarmDiaryDto> getFarmDiaryByPlantId(ObjectId id);

    void create(FarmDiaryDto farmDiaryDto);

    void deleteById(ObjectId id);

    void update(ObjectId id, FarmDiaryDto farmDiaryDto);

    void deleteByPlantId(ObjectId id);
}
