package com.kinto.kintobackend.service;


import com.kinto.kintobackend.dto.LocationDto;
import com.kinto.kintobackend.model.Location;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface LocationService {
    void create(LocationDto locationDto);

    void update(ObjectId id, LocationDto locationDto);

    void deleteById(ObjectId id);


    Map<ObjectId, LocationDto> getMapLocations(List<ObjectId> locationIds);

    LocationDto getById(ObjectId locationId);


    Page<LocationDto> findAllPagination(String orgId, Pageable pageable);

    List<LocationDto> getAll(String orgId);
}
