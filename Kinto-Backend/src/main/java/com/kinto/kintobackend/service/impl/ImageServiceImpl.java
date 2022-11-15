package com.kinto.kintobackend.service.impl;

import com.kinto.kintobackend.config.AmazonS3Client;
import com.kinto.kintobackend.converter.ImageConverter;
import com.kinto.kintobackend.dto.ImageDto;
import com.kinto.kintobackend.exception.ResourceNotFoundException;
import com.kinto.kintobackend.model.Image;
import com.kinto.kintobackend.repository.ImageRepository;
import com.kinto.kintobackend.service.ImageService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ImageConverter imageConverter;

    @Autowired
    AmazonS3Client s3Client;

    String bucketName = "kinto-backend-images";


    @Override
    public List<ImageDto> findAllImage() {
        List<Image> images = imageRepository.findAll();
        List<ImageDto> imageDto = new ArrayList<>();
        if (images.size() > 0) {
            images.forEach(item -> imageDto.add(imageConverter.convert(item)));
            return imageDto;
        }
        else return new ArrayList<>();
    }

    @Override
    public void create(ObjectId plantId, String orgId,List<MultipartFile> files) throws IOException {
        if (files.isEmpty()){
            throw new RuntimeException("Failed to store empty file.");
        }else {
            for (MultipartFile file : files){
                Image image = new Image();
                //check is file?
                if (!checkIsImage(file)) {
                    throw new RuntimeException("You can only upload image file.");
                }
                // file must <= 5mb
                float fileSizeMegabytes = file.getSize() / 1_000_000f;
                if (fileSizeMegabytes > 5.0f) {
                    throw new RuntimeException("File must be <= 5Mb");
                }
                URL result= s3Client.uploadFile(bucketName,file);
                image.setBucketName(bucketName);
                image.setObjectName(result.toExternalForm());
                image.setName(result.getFile().replace("/",""));
                image.setPlantId(plantId);
                image.setOrgId(new ObjectId(orgId));
                image.setCreateAt(Instant.now());
                image.setUpdateAt(Instant.now());
                imageRepository.save(image);
            }
        }
    }

    @Override
    public ImageDto getUrl(ObjectId id) {
        Optional<Image> imageOptional  = imageRepository.findById(id);
        if(imageOptional.isPresent()){
            String url = s3Client.getPreSignedDownloadUrl(bucketName,imageOptional.get().getName());
            imageOptional.get().setName(url);
            return imageConverter.convert(imageOptional.get());
        }else throw new ResourceNotFoundException(id + " not found!");

    }

    @Override
    public void updateStatus(ObjectId id) {
        Optional<Image> imageOptional  = imageRepository.findById(id);
        if(imageOptional.isPresent()){
            imageOptional.get().setUpdateAt(Instant.now());
            imageRepository.save(imageOptional.get());
        }else throw new ResourceNotFoundException(id + " not found!");
    }

    @Override
    public void deleteById(ObjectId id){
        Optional<Image> imageOptional  = imageRepository.findById(id);
        if(imageOptional.isPresent()){
            s3Client.deleteFile(bucketName,imageOptional.get().getName());
            imageRepository.deleteById(id);
            System.out.println("delete success image by id: "+ id);
        }else throw new ResourceNotFoundException(id + " not found!");
    }

    @Override
    public void createImage(ImageDto item) {
        imageRepository.save(imageConverter.convert(item));
    }

    @Override
    public List<ImageDto> getImageByPlantId(ObjectId id) {
        List<Image> images = imageRepository.findByPlantId(id);
        if (images.size() > 0){
            List<ImageDto> imageDtos = new ArrayList<>();

            images.forEach(item -> {
                item.setObjectName(getUrl(item.getId()).getName());
                imageDtos.add(imageConverter.convert(item));
            });
            return  imageDtos;
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteByPlantId(ObjectId id) {
        List<Image> images = imageRepository.findByPlantId(id);
        imageRepository.deleteAll(images);
    }

    public boolean checkIsImage(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png","jpg","jpeg","bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

//    @Scheduled(fixedRate = 60 * 60 *1000,initialDelay =60 * 60 *1000)
//    public void scheduleFixedRateTask() {
//        List<Image> images = imageRepository.findByStatusIsFalse();
//        if (images.size() > 0){
//            imageRepository.deleteAll(images);
//        }else {
//            System.out.println("Khong co hinh nao het");
//        }
//    }
}
