package com.kinto.kintobackend.service;

import com.kinto.kintobackend.dto.LocationDto;
import com.kinto.kintobackend.dto.PlantTypeDto;
import com.kinto.kintobackend.model.PlantType;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PlantTypeService {
    void create(PlantTypeDto plantTypeDto);

    PlantTypeDto getById(ObjectId id);

    void update(ObjectId id, PlantTypeDto plantTypeDto);

    void deleteById(ObjectId id);

    PlantTypeDto findById(ObjectId plantTypeId);

    Map<ObjectId, PlantTypeDto> getMapPlantTypes(List<ObjectId> plantTypeIds);

    Page<PlantTypeDto> findByPagination(String orgId, Pageable pageable);

    List<PlantTypeDto> getAll(String orgId);

}
