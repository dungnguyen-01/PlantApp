package com.kinto.kintobackend.service;

import com.kinto.kintobackend.dto.ImageDto;
import com.kinto.kintobackend.model.Image;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<ImageDto> findAllImage();

    void create(ObjectId plantId,  String orgId, List<MultipartFile> files) throws IOException;

    ImageDto getUrl(ObjectId fileName);

    void updateStatus(ObjectId id);

    void deleteById(ObjectId id);

    void createImage(ImageDto item);

    List<ImageDto> getImageByPlantId(ObjectId id);

    void deleteByPlantId(ObjectId id);
}
