package com.kinto.kintobackend.service.impl;

import com.kinto.kintobackend.converter.FarmDiaryConverter;
import com.kinto.kintobackend.dto.FarmDiaryDto;
import com.kinto.kintobackend.exception.ResourceNotFoundException;
import com.kinto.kintobackend.model.FarmDiary;
import com.kinto.kintobackend.repository.FarmDiaryRepository;
import com.kinto.kintobackend.service.FarmDiaryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FarmDiaryServiceImpl implements FarmDiaryService {
    @Autowired
    FarmDiaryRepository farmDiaryRepository;
    @Autowired
    FarmDiaryConverter farmDiaryConverter;

    @Override
    public FarmDiaryDto getById(ObjectId farmDiaryId) {
        Optional<FarmDiary> farmDiary = farmDiaryRepository.findById(farmDiaryId);
        if (farmDiary.isPresent()) return farmDiaryConverter.convert(farmDiary.get());
        else throw new ResourceNotFoundException(farmDiaryId+" not found!");
    }


    @Override
    public List<FarmDiaryDto> getFarmDiaryByPlantId(ObjectId id) {
        List<FarmDiary> farmDiaries = farmDiaryRepository.findByPlantId(id);
        if (farmDiaries.size() > 0){
            List<FarmDiaryDto> farmDiaryDtos =  new ArrayList<>();
            farmDiaries.forEach(item -> farmDiaryDtos.add(farmDiaryConverter.convert(item)));
            return farmDiaryDtos;
        } else return new ArrayList<>();
    }

    @Override
    public void create(FarmDiaryDto farmDiaryDto) {
        farmDiaryDto.setUpdateAt(Instant.now());
        farmDiaryDto.setCreateAt(Instant.now());
        farmDiaryRepository.save(farmDiaryConverter.convert(farmDiaryDto));
    }

    @Override
    public void deleteById(ObjectId id) {
        farmDiaryRepository.deleteById(id);
    }

    @Override
    public void update(ObjectId id, FarmDiaryDto farmDiaryDto) {
        Optional<FarmDiary> farmDiary = farmDiaryRepository.findById(id);
        if (farmDiary.isPresent()) {
            FarmDiary farmDiaryUpdate = farmDiary.get();
            farmDiaryUpdate.setName(farmDiaryDto.getName());
            farmDiaryUpdate.setUpdateAt(Instant.now());
            farmDiaryRepository.save(farmDiaryUpdate);
        } else throw new ResourceNotFoundException(id+ " not found!") ;
    }

    @Override
    public void deleteByPlantId(ObjectId id) {
        List<FarmDiary> farmDiaries = farmDiaryRepository.findByPlantId(id);
        if (farmDiaries.size() > 0) {
            farmDiaryRepository.deleteAll(farmDiaries);
        }
    }
}
