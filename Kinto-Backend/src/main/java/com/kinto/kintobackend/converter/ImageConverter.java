package com.kinto.kintobackend.converter;

import com.kinto.kintobackend.dto.ImageDto;
import com.kinto.kintobackend.model.Image;
import com.kinto.kintobackend.service.OrganizationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {
    @Autowired
    OrganizationService organizationService;

    public Image convert(ImageDto imageDto){
        Image image = new Image();

        image.setId(new ObjectId(imageDto.getId()));
        image.setName(imageDto.getName());
        image.setObjectName(imageDto.getObjectName());
        image.setBucketName(imageDto.getBucketName());
        image.setPlantId(imageDto.getPlantId());
        image.setOrgId(new ObjectId(imageDto.getOrganization().getId()));
        image.setCreateAt(imageDto.getCreateAt());
        image.setUpdateAt(imageDto.getUpdateAt());
        return image;
    }

    public ImageDto convert(Image image){
        ImageDto imageDto = new ImageDto();

        imageDto.setId(image.getId().toString());
        imageDto.setName(image.getName());
        imageDto.setObjectName(image.getObjectName());
        imageDto.setBucketName(image.getBucketName());
        imageDto.setPlantId(image.getPlantId());
        imageDto.setCreateAt(image.getCreateAt());
        imageDto.setUpdateAt(image.getUpdateAt());

        return  imageDto;
    }
}
