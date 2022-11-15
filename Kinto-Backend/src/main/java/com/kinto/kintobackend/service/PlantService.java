package com.kinto.kintobackend.service;


import com.kinto.kintobackend.dto.PlantDto;
import com.kinto.kintobackend.model.Plant;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlantService {
    void create(PlantDto plantDto);

    PlantDto getByIdPlant(ObjectId id);

    void update(ObjectId id, PlantDto plantDto);

    void deleteById(ObjectId id);

    Page<PlantDto> findAllPagination(String orgId, Pageable pageable);

    Page<PlantDto> findFilterPagination(String orgId, String plantType, String location, String pic, Pageable pageable);

    Page<PlantDto> findByLocationPagination(String orgId, String location, Pageable pageable);

    Page<PlantDto> findByPlantTypePagination(String orgId, String plantType, Pageable pageable);

    Page<PlantDto> findPicPagination(String orgId, String pic, Pageable pageable);
}
