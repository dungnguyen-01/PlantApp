package com.kinto.kintobackend.service.impl;

import com.amazonaws.services.ssmcontacts.model.Plan;
import com.kinto.kintobackend.converter.PlantConverter;
import com.kinto.kintobackend.converter.PlantTypeConverter;
import com.kinto.kintobackend.dto.PlantTypeDto;
import com.kinto.kintobackend.exception.AlreadyExistsCollectionException;
import com.kinto.kintobackend.exception.ResourceNotFoundException;
import com.kinto.kintobackend.model.FarmDiary;
import com.kinto.kintobackend.model.PlantType;
import com.kinto.kintobackend.repository.PlantTypeRepository;
import com.kinto.kintobackend.service.OrganizationService;
import com.kinto.kintobackend.service.PlantTypeService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlantTypeServiceImpl implements PlantTypeService {
    @Autowired
    PlantTypeRepository plantTypeRepository;
    @Autowired
    PlantTypeConverter plantTypeConverter;
    @Autowired
    OrganizationService organizationService;

    @Override
    public List<PlantTypeDto> getAll(String orgId) {
        List<PlantType> plantTypes = plantTypeRepository.findByStatusIsTrueAndOrgIdEquals(new ObjectId(orgId));
        if (plantTypes.size() > 0) {
            List<PlantTypeDto> plantTypeDtos = new ArrayList<>();
            plantTypes.forEach(item -> {
                plantTypeDtos.add(plantTypeConverter.convert(item));
            });
            return plantTypeDtos;
        }
        return new ArrayList<>();
    }

    @Override
    public Page<PlantTypeDto> findByPagination(String orgId, Pageable pageable) {
        Page<PlantType> plantTypes = plantTypeRepository.findAllByStatusIsTrueAndOrgIdEquals(new ObjectId(orgId), pageable);
        List<PlantTypeDto> plantTypeDtos = new ArrayList<>();
        if (plantTypes.getSize() > 0) {
            plantTypes.forEach(item -> {
                plantTypeDtos.add(plantTypeConverter.convert(item));
            });
            return new PageImpl<>(plantTypeDtos, plantTypes.getPageable(), plantTypes.getTotalElements());
        } else return new PageImpl<>(new ArrayList<>());
    }


    @Override
    public void create(PlantTypeDto plantTypeDto) {
        Optional<PlantType> plantTypeOptional = plantTypeRepository.findByNamePlantType(plantTypeDto.getNamePlantType());
        if (plantTypeOptional.isPresent())
            throw new AlreadyExistsCollectionException("NamePlantType: " + plantTypeDto.getNamePlantType() + " already exists");
        if (!checkLetter(plantTypeDto.getCode()) == true)
            throw new ResourceNotFoundException("Enter 3 to 5 characters and numbers (A-Z, 0-9) without spaces and special characters.");
        if (plantTypeRepository.existsByCode(plantTypeDto.getCode()) == true)
            throw new AlreadyExistsCollectionException("Code: " + plantTypeDto.getCode() + " already exits!");
        PlantType plantType = plantTypeConverter.convert(plantTypeDto);
        plantType.setStatus(true);
        plantType.setCreateAt(Instant.now());
        plantType.setUpdateAt(Instant.now());
        plantTypeRepository.save(plantType);
        System.out.println(plantTypeDto);
    }

    public static boolean checkLetter(String code) {
        boolean result = code.matches("^[A-Z0-9]*$");
        if (result == true && code != null || code.length() <= 2 || code.length() >= 6) return true;
        else return false;
    }

    @Override
    public PlantTypeDto getById(ObjectId id) {
        Optional<PlantType> plantType = plantTypeRepository.findById(id);
        if (plantType.isPresent()) {
            PlantTypeDto plantTypeDto = plantTypeConverter.convert(plantType.get());
            plantTypeDto.setOrganization(organizationService.getByIdOrganization(plantType.get().getOrgId()));
            return plantTypeDto;
        } else throw new ResourceNotFoundException(id + " not found!");
    }

    @Override
    public void update(ObjectId id, PlantTypeDto plantTypeDto) {
        Optional<PlantType> plantTypeWithId = plantTypeRepository.findById(id);
        Optional<PlantType> plantTypeWithSameName = plantTypeRepository.findByNamePlantType(plantTypeDto.getNamePlantType());
        Optional<PlantType> plantTypeWithSameCode = plantTypeRepository.findByCode(plantTypeDto.getCode());
        if (!checkLetter(plantTypeDto.getCode()) == true)
            throw new ResourceNotFoundException("Enter 3 to 5 characters and numbers (A-Z, 0-9) without spaces and special characters.");
        if (!plantTypeWithId.isPresent()) throw new ResourceNotFoundException(id + " not found!");
        if (plantTypeWithSameName.isPresent() && !plantTypeWithSameName.get().getId().equals(id))
            throw new AlreadyExistsCollectionException("NamePlantType: "+plantTypeDto.getNamePlantType() + " already exists");
        if (plantTypeWithSameCode.isPresent() && !plantTypeWithSameCode.get().getId().equals(id))
            throw new AlreadyExistsCollectionException("Code: "+plantTypeDto.getCode() + " already exists");

        PlantType plantTypeSave = plantTypeWithId.get();
        plantTypeSave.setStatus(true);
        plantTypeSave.setCode(plantTypeDto.getCode());
        plantTypeSave.setOrgId(new ObjectId(plantTypeDto.getOrganization().getId()));
        plantTypeSave.setNamePlantType(plantTypeDto.getNamePlantType());
        plantTypeSave.setUpdateAt(Instant.now());

        plantTypeRepository.save(plantTypeSave);
    }

    @Override
    public void deleteById(ObjectId id) {
        Optional<PlantType> plantTypeOptional = plantTypeRepository.findById(id);
        if (plantTypeOptional.isPresent()) {
            plantTypeOptional.get().setStatus(false);
            plantTypeRepository.save(plantTypeOptional.get());
        } else throw new ResourceNotFoundException(id + " not found!");
    }

    @Override
    public PlantTypeDto findById(ObjectId plantTypeId) {
        Optional<PlantType> plantType = plantTypeRepository.findById(plantTypeId);
        if (plantType.isPresent()) return plantTypeConverter.convert(plantType.get());
        else throw new ResourceNotFoundException(plantTypeId + " not found!");
    }

    @Override
    public Map<ObjectId, PlantTypeDto> getMapPlantTypes(List<ObjectId> plantTypeIds) {
        List<PlantType> plantTypes = plantTypeRepository.findByIdIn(plantTypeIds);
        return plantTypes.stream()
                .collect(Collectors.toMap(PlantType::getId, l -> plantTypeConverter.convert(l)));
    }

}
