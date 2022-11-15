package com.kinto.kintobackend.converter;


import com.kinto.kintobackend.dto.PlantTypeDto;
import com.kinto.kintobackend.model.PlantType;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class PlantTypeConverter {

    public PlantType convert(PlantTypeDto plantTypeDto){

        PlantType plantType = new PlantType();
        plantType.setNamePlantType(plantTypeDto.getNamePlantType());
        plantType.setOrgId(new ObjectId(plantTypeDto.getOrganization().getId()));
        plantType.setStatus(plantTypeDto.getStatus());
        plantType.setCode(plantTypeDto.getCode());
        plantType.setCreateAt(plantTypeDto.getCreateAt());
        plantType.setUpdateAt(plantTypeDto.getUpdateAt());

        return plantType;
    }

    public PlantTypeDto convert(PlantType plantType){

        PlantTypeDto plantTypeDto = new PlantTypeDto();
        plantTypeDto.setId(plantType.getId().toString());
        plantTypeDto.setNamePlantType(plantType.getNamePlantType());
        plantTypeDto.setStatus(plantType.getStatus());
        plantTypeDto.setCode(plantType.getCode());
        plantTypeDto.setCreateAt(plantType.getCreateAt());
        plantTypeDto.setUpdateAt(plantType.getUpdateAt());

        return plantTypeDto;
    }
}
