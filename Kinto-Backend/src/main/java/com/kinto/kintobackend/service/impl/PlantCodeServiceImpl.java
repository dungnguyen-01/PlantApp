package com.kinto.kintobackend.service.impl;

import com.kinto.kintobackend.converter.PlantCodeConverter;
import com.kinto.kintobackend.dto.PlantCodeDto;
import com.kinto.kintobackend.exception.ResourceNotFoundException;
import com.kinto.kintobackend.model.PlantCode;
import com.kinto.kintobackend.repository.PlantCodeRepository;
import com.kinto.kintobackend.service.PlantCodeService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PlantCodeServiceImpl implements PlantCodeService {

    @Autowired
    PlantCodeRepository plantCodeRepository;

    @Autowired
    PlantCodeConverter plantCodeConverter;

    @Override
    public void create(PlantCodeDto plantCodeDto) {
        PlantCode plantCode = plantCodeConverter.convert(plantCodeDto);
        plantCodeRepository.save(plantCode);
    }

    @Override
    public PlantCodeDto getCodeByIds(String plantId, String plantTypeId, String locationId) {
        Optional<PlantCode> check = plantCodeRepository.findByPlantIdAndAndPlantTypeIdAndAndLocationId(new ObjectId(plantId), new ObjectId(plantTypeId), new ObjectId(locationId));
        if (check.isPresent()) {
            return plantCodeConverter.convert(check.get());
        } else throw new ResourceNotFoundException("PlantCode not found!");

    }
}
