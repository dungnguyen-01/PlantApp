package com.kinto.kintobackend.service;

import com.kinto.kintobackend.dto.PlantCodeDto;

public interface PlantCodeService {
    void create(PlantCodeDto plantCodeDto);

    PlantCodeDto getCodeByIds(String plantId, String plantTypeId, String locationId);
}
