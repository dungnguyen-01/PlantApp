package com.kinto.kintobackend.converter;


import com.kinto.kintobackend.dto.FarmDiaryDto;
import com.kinto.kintobackend.model.FarmDiary;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class FarmDiaryConverter {

    public FarmDiary convert(FarmDiaryDto farmDiaryDto) {
        FarmDiary farmDiary = new FarmDiary();
        farmDiary.setName(farmDiaryDto.getName());
        farmDiary.setPlantId(farmDiaryDto.getPlantId());
        farmDiary.setOrgId(new ObjectId(farmDiaryDto.getOrganization().getId()));
        farmDiary.setCreateAt(farmDiaryDto.getCreateAt());
        farmDiary.setUpdateAt(farmDiaryDto.getUpdateAt());

        return farmDiary;
    }

    public FarmDiaryDto convert(FarmDiary farmDiary) {
        FarmDiaryDto farmDiaryDto = new FarmDiaryDto();
        farmDiaryDto.setId(farmDiary.getId().toString());
        farmDiaryDto.setName(farmDiary.getName());
        farmDiaryDto.setPlantId(farmDiary.getPlantId());
        farmDiaryDto.setCreateAt(farmDiary.getCreateAt());
        farmDiaryDto.setUpdateAt(farmDiary.getUpdateAt());

        return farmDiaryDto;
    }
}
