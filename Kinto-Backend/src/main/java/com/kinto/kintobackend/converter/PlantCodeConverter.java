package com.kinto.kintobackend.converter;

import com.kinto.kintobackend.dto.PlantCodeDto;
import com.kinto.kintobackend.model.PlantCode;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class PlantCodeConverter {

    public PlantCode convert(PlantCodeDto plantCodeDto) {
        PlantCode plantCode = new PlantCode();
        plantCode.setPlantId(new ObjectId(plantCodeDto.getPlantId()));
        plantCode.setPlantTypeId(new ObjectId(plantCodeDto.getPlantTypeId()));
        plantCode.setLocationId(new ObjectId(plantCodeDto.getLocationId()));

        return plantCode;
    }

    public PlantCodeDto convert(PlantCode plantCode) {
        PlantCodeDto plantCodeDto = new PlantCodeDto();
        plantCodeDto.setPlantId(plantCode.getPlantId().toString());
        plantCodeDto.setPlantTypeId(plantCode.getPlantTypeId().toString());
        plantCodeDto.setLocationId(plantCode.getLocationId().toString());
        return plantCodeDto;
    }
}
