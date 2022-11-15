package com.kinto.kintobackend.dto;

import com.kinto.kintobackend.model.Image;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.List;

@Data
public class PlantDto {

    private String id;
    private String cultureMedium;
    private LocationDto location;
    private UserDto user;
    private PlantTypeDto plantType;
    private List<FarmDiaryDto> farmDiaryDtos;
    private List<ImageDto> imageDtos;
    private ObjectId orgId;
    private String code;
    private Instant createAt;
    private Instant updateAt;
}
