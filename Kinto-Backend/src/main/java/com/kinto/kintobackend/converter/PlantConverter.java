package com.kinto.kintobackend.converter;

import com.kinto.kintobackend.dto.PlantDto;
import com.kinto.kintobackend.model.Plant;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class PlantConverter {


    public Plant convert(PlantDto plantDto) {
        plantDto.setId(new ObjectId().toString());
        Plant plant = new Plant();
        plant.setId(new ObjectId(plantDto.getId()));
        plant.setCultureMedium(plantDto.getCultureMedium());
        plant.setPlantTypeId(new ObjectId(plantDto.getPlantType().getId()));
        plant.setLocationId(new ObjectId(plantDto.getLocation().getId()));
        plant.setPicId(new ObjectId(plantDto.getUser().getId()));
        plant.setCode(plantDto.getCode());
        plant.setUpdateAt(plantDto.getUpdateAt());
        plant.setCreateAt(plantDto.getCreateAt());

        return plant;
    }

    public PlantDto convert(Plant plant) {

        PlantDto plantDto = new PlantDto();
        plantDto.setId(plant.getId().toString());
        plantDto.setCultureMedium(plant.getCultureMedium());
        plantDto.setCode(plant.getCode());
        plantDto.setUpdateAt(plant.getUpdateAt());
        plantDto.setCreateAt(plant.getCreateAt());

        return plantDto;
    }
}
