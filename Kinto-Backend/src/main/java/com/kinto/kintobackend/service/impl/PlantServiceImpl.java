package com.kinto.kintobackend.service.impl;

import com.kinto.kintobackend.converter.ImageConverter;
import com.kinto.kintobackend.converter.PlantConverter;
import com.kinto.kintobackend.dto.*;
import com.kinto.kintobackend.exception.*;
import com.kinto.kintobackend.model.*;
import com.kinto.kintobackend.repository.*;
import com.kinto.kintobackend.service.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlantServiceImpl implements PlantService {

    @Autowired
    PlantRepository plantRepository;
    @Autowired
    PlantConverter plantConverter;
    @Autowired
    PlantTypeService plantTypeService;
    @Autowired
    LocationService locationService;
    @Autowired
    FarmDiaryService farmDiaryService;
    @Autowired
    ImageService imageService;
    @Autowired
    ImageConverter imageConverter;
    @Autowired
    UserService userService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    PlantCodeService plantCodeService;

    @Override
    public void create(PlantDto plantDto) {
        PlantTypeDto plantTypeOptional = plantTypeService.findById(new ObjectId(plantDto.getPlantType().getId()));
        LocationDto locationOptional = locationService.getById(new ObjectId(plantDto.getLocation().getId()));
        if (plantTypeOptional != null) {
            if (locationOptional != null) {
                    PlantCodeDto plantCodeDto = new PlantCodeDto();
                    String code = this.checkCode(this.getAlphaNumericString());
                    if (plantRepository.existsByCode(code) == true) code = this.getAlphaNumericString();

                    Plant plant = plantConverter.convert(plantDto);
                    plant.setPicId(new ObjectId(plantDto.getUser().getId()));
                    plant.setPlantTypeId(new ObjectId(plantDto.getPlantType().getId()));
                    plant.setLocationId(new ObjectId(plantDto.getLocation().getId()));
                    plant.setOrgId(plantDto.getOrgId());
                    plant.setUpdateAt(Instant.now());
                    plant.setCreateAt(Instant.now());
                    plant.setCode(code);
                    plantRepository.save(plant);

                    plantCodeDto.setPlantId(plant.getId().toString());
                    plantCodeDto.setLocationId(plantDto.getLocation().getId());
                    plantCodeDto.setPlantTypeId(plantDto.getPlantType().getId());
                    plantCodeService.create(plantCodeDto);

            } else throw new ResourceNotFoundException(plantDto.getLocation().getId() + " not found!");
        } else throw new ResourceNotFoundException(plantDto.getCultureMedium() + " not found!");
    }

    public String checkCode(String code) {
        boolean checkCode = plantRepository.existsByCode(code);
        if (checkCode == false) {
            return code;
        } else return this.getAlphaNumericString();
    }

    @Override
    public void update(ObjectId id, PlantDto plantDto) {
        Optional<Plant> optionalPlantFindById = plantRepository.findById(id);
        LocationDto locationOptional = locationService.getById(new ObjectId(plantDto.getLocation().getId()));
        PlantTypeDto plantTypeOptional = plantTypeService.findById(new ObjectId(plantDto.getPlantType().getId()));

        if (optionalPlantFindById.isPresent()) {
            if (locationOptional == null)
                throw new ResourceNotFoundException(plantDto.getLocation().getId() + " not found!");
            if (plantTypeOptional == null)
                throw new ResourceNotFoundException(plantDto.getPlantType().getId() + " not found!");
            Plant plantUpdate = optionalPlantFindById.get();

            plantUpdate.setUpdateAt(Instant.now());
            plantUpdate.setPlantTypeId(new ObjectId(plantDto.getPlantType().getId()));
            plantUpdate.setCultureMedium(plantDto.getCultureMedium());
            plantUpdate.setLocationId(new ObjectId(plantDto.getLocation().getId()));
            plantUpdate.setPicId(new ObjectId(plantDto.getUser().getId()));
            plantUpdate.setOrgId(plantDto.getOrgId());

            plantRepository.save(plantUpdate);
        } else throw new ResourceNotFoundException(id + " not found!");
    }

    public String getCode(String plantId, String locationId, String plantTypeId) {
        PlantCodeDto plantCodeDto = plantCodeService.getCodeByIds(plantId, plantTypeId, locationId);
        PlantTypeDto plantTypeDto = plantTypeService.getById(new ObjectId(plantCodeDto.getPlantTypeId()));
        LocationDto locationDto = locationService.getById(new ObjectId(plantCodeDto.getLocationId()));
        Optional<Plant> plant = plantRepository.findById(new ObjectId(plantCodeDto.getPlantId()));
        String code = plantTypeDto.getCode()+"-"+locationDto.getCode()+"-"+plant.get().getCode();
        return code;

    }


    @Override
    public PlantDto getByIdPlant(ObjectId id) {
        Optional<Plant> plantOptional = plantRepository.findById(id);
        List<FarmDiaryDto> farmDiaries = farmDiaryService.getFarmDiaryByPlantId(id);
        List<ImageDto> imageDtos = imageService.getImageByPlantId(id);
        if (plantOptional.isPresent()) {
            LocationDto locationDto = locationService.getById(plantOptional.get().getLocationId());
            UserDto userDto = userService.getByIdUser(plantOptional.get().getPicId());
            PlantTypeDto plantTypeDto = plantTypeService.getById(plantOptional.get().getPlantTypeId());
            PlantDto plantDto = plantConverter.convert(plantOptional.get());
            plantDto.setFarmDiaryDtos(farmDiaries);
            plantDto.setImageDtos(imageDtos);
            plantDto.setLocation(locationDto);
            plantDto.setPlantType(plantTypeDto);
            plantDto.setCode(this.getCode(plantDto.getId(), locationDto.getId(), plantTypeDto.getId()));
            plantDto.setUser(userDto);
            return plantDto;
        } else throw new ResourceNotFoundException(id + " not found!");
    }

    @Override
    public void deleteById(ObjectId id) {
        Optional<Plant> plantOptional = plantRepository.findById(id);
        if (plantOptional.isPresent()) plantRepository.deleteById(plantOptional.get().getId());
        else throw new ResourceNotFoundException(id + " not found!");
    }


    @Override
    public Page<PlantDto> findFilterPagination(String orgId, String plantType, String location, String pic, Pageable pageable) {
        if (plantType != "" && pic == "" && location == "") return this.findByPlantTypePagination(orgId, plantType, pageable);
        else if (pic != "" && plantType == "" && location == "") {
            return this.findPicPagination(orgId, pic, pageable);
        }else if (location != "" && pic == "" && plantType == "")
            return this.findByLocationPagination(orgId, location, pageable);
        else if (plantType != "" && pic != "") return this.findByPlantTypeIdAndPic(orgId, plantType, pic, pageable);
        else if (plantType != "" && location != "")
            return this.findByPlantTypeAndLocation(orgId, plantType, location, pageable);
        else if (pic != "" && location != "") return this.findByPicAndLocation(orgId, pic, location, pageable);
        else {
            Page<Plant> plants = plantRepository.findByOrgIdEqualsAndPlantTypeIdAndLocationIdAndPicId(new ObjectId(orgId),
                    new ObjectId(plantType), new ObjectId(location), new ObjectId(pic), pageable);
            List<PlantDto> resPlants = new ArrayList<>();

            List<ObjectId> locationIds = plants.stream().map(Plant::getLocationId)
                    .collect(Collectors.toList());
            List<ObjectId> plantTypes = plants.stream().map(Plant::getPlantTypeId)
                    .collect(Collectors.toList());
            List<ObjectId> Users = plants.stream().map(Plant::getPicId)
                    .collect(Collectors.toList());
            Map<ObjectId, LocationDto> mapLocations = locationService.getMapLocations(locationIds);
            Map<ObjectId, PlantTypeDto> mapPlantTypes = plantTypeService.getMapPlantTypes(plantTypes);
            Map<ObjectId, UserDto> mapUsers = userService.getMapUsers(Users);
            for (Plant plant : plants.getContent()) {
                LocationDto locationDto = mapLocations.get(plant.getLocationId());
                PlantTypeDto plantTypeDto = mapPlantTypes.get(plant.getPlantTypeId());
                UserDto userDto = mapUsers.get(plant.getPicId());
                PlantDto plantDto = plantConverter.convert(plant);
                plantDto.setLocation(locationDto);
                plantDto.setPlantType(plantTypeDto);
                plantDto.setUser(userDto);
                plantDto.setCode(this.getCode(plant.getId().toString(), locationDto.getId(), plantTypeDto.getId()));
                resPlants.add(plantDto);
            }
            return new PageImpl<>(resPlants, plants.getPageable(), plants.getTotalElements());
        }
    }

    @Override
    public Page<PlantDto> findAllPagination(String orgId, Pageable pageable) {
        Page<Plant> plants = plantRepository.findByOrgIdEquals(new ObjectId(orgId) ,pageable);
        List<PlantDto> resPlants = new ArrayList<>();
        List<ObjectId> locationIds = plants.stream().map(Plant::getLocationId)
                .collect(Collectors.toList());
        List<ObjectId> plantTypes = plants.stream().map(Plant::getPlantTypeId)
                .collect(Collectors.toList());
        List<ObjectId> Users = plants.stream().map(Plant::getPicId)
                .collect(Collectors.toList());

        Map<ObjectId, LocationDto> mapLocations = locationService.getMapLocations(locationIds);
        Map<ObjectId, PlantTypeDto> mapPlantTypes = plantTypeService.getMapPlantTypes(plantTypes);
        Map<ObjectId, UserDto> mapUsers = userService.getMapUsers(Users);

        for (Plant plant : plants.getContent()) {
            LocationDto locationDto = mapLocations.get(plant.getLocationId());
            PlantTypeDto plantTypeDto = mapPlantTypes.get(plant.getPlantTypeId());
            UserDto userDto = mapUsers.get(plant.getPicId());

            PlantDto plantDto = plantConverter.convert(plant);
            plantDto.setLocation(locationDto);
            plantDto.setPlantType(plantTypeDto);
            plantDto.setUser(userDto);
            plantDto.setCode(this.getCode(plant.getId().toString(), locationDto.getId(), plantTypeDto.getId()));

            resPlants.add(plantDto);
        }
        return new PageImpl<>(resPlants, plants.getPageable(), plants.getTotalElements());

    }

    @Override
    public Page<PlantDto> findByLocationPagination(String orgId, String location, Pageable pageable) {
        List<PlantDto> resPlants = new ArrayList<>();
        Page<Plant> plants = plantRepository.findByOrgIdEqualsAndLocationId(new ObjectId(orgId) ,new ObjectId(location), pageable);
        List<ObjectId> Users = plants.stream().map(Plant::getPicId)
                .collect(Collectors.toList());
        List<ObjectId> plantTypes = plants.stream().map(Plant::getPlantTypeId)
                .collect(Collectors.toList());
        Map<ObjectId, UserDto> mapUsers = userService.getMapUsers(Users);
        Map<ObjectId, PlantTypeDto> mapPlantTypes = plantTypeService.getMapPlantTypes(plantTypes);
        for (Plant plant : plants.getContent()) {
            LocationDto locationDto = locationService.getById(plant.getLocationId());
            PlantTypeDto plantTypeDto = mapPlantTypes.get(plant.getPlantTypeId());
            UserDto userDto = mapUsers.get(plant.getPicId());
            PlantDto plantDto = plantConverter.convert(plant);

            plantDto.setLocation(locationDto);
            plantDto.setPlantType(plantTypeDto);
            plantDto.setPlantType(plantTypeDto);
            plantDto.setUser(userDto);
            plantDto.setCode(this.getCode(plant.getId().toString(), locationDto.getId(), plantTypeDto.getId()));

            resPlants.add(plantDto);
        }
        return new PageImpl<>(resPlants, plants.getPageable(), plants.getTotalElements());
    }

    public Page<PlantDto> findByPlantTypeIdAndPic(String orgId, String plantType, String pic, Pageable pageable) {
        List<PlantDto> resPlants = new ArrayList<>();
        Page<Plant> plants = plantRepository.findByOrgIdEqualsAndPlantTypeIdAndPicId(new ObjectId(orgId) ,new ObjectId(plantType), new ObjectId(pic), pageable);
        List<ObjectId> Users = plants.stream().map(Plant::getPicId)
                .collect(Collectors.toList());
        List<ObjectId> plantTypes = plants.stream().map(Plant::getPlantTypeId)
                .collect(Collectors.toList());
        Map<ObjectId, UserDto> mapUsers = userService.getMapUsers(Users);
        Map<ObjectId, PlantTypeDto> mapPlantTypes = plantTypeService.getMapPlantTypes(plantTypes);
        for (Plant plant : plants.getContent()) {
            LocationDto locationDto = locationService.getById(plant.getLocationId());
            PlantTypeDto plantTypeDto = plantTypeService.getById(plant.getPlantTypeId());
            UserDto userDto = userService.getByIdUser(plant.getPicId());
            PlantDto plantDto = plantConverter.convert(plant);

            plantDto.setLocation(locationDto);
            plantDto.setPlantType(plantTypeDto);
            plantDto.setPlantType(plantTypeDto);
            plantDto.setUser(userDto);
            plantDto.setCode(this.getCode(plant.getId().toString(), locationDto.getId(), plantTypeDto.getId()));

            resPlants.add(plantDto);
        }
        return new PageImpl<>(resPlants, plants.getPageable(), plants.getTotalElements());
    }

    public Page<PlantDto> findByPlantTypeAndLocation(String orgId, String plantType, String location, Pageable pageable) {
        List<PlantDto> resPlants = new ArrayList<>();
        Page<Plant> plants = plantRepository.findByOrgIdEqualsAndPlantTypeIdAndLocationId(new ObjectId(orgId) ,new ObjectId(plantType), new ObjectId(location), pageable);
        List<ObjectId> Users = plants.stream().map(Plant::getPicId)
                .collect(Collectors.toList());
        Map<ObjectId, UserDto> mapUsers = userService.getMapUsers(Users);

        for (Plant plant : plants.getContent()) {
            LocationDto locationDto = locationService.getById(plant.getLocationId());
            PlantTypeDto plantTypeDto = plantTypeService.getById(plant.getPlantTypeId());
            UserDto userDto = mapUsers.get(plant.getPicId());
            PlantDto plantDto = plantConverter.convert(plant);

            plantDto.setLocation(locationDto);
            plantDto.setPlantType(plantTypeDto);
            plantDto.setUser(userDto);
            plantDto.setCode(this.getCode(plant.getId().toString(), locationDto.getId(), plantTypeDto.getId()));

            resPlants.add(plantDto);
        }
        return new PageImpl<>(resPlants, plants.getPageable(), plants.getTotalElements());
    }


    public Page<PlantDto> findByPicAndLocation(String orgId, String pic, String location, Pageable pageable) {
        List<PlantDto> resPlants = new ArrayList<>();
        Page<Plant> plants = plantRepository.findByOrgIdEqualsAndPicIdAndLocationId(new ObjectId(orgId) ,new ObjectId(pic), new ObjectId(location), pageable);
        List<ObjectId> plantTypes = plants.stream().map(Plant::getPlantTypeId)
                .collect(Collectors.toList());
        Map<ObjectId, PlantTypeDto> mapPlantTypes = plantTypeService.getMapPlantTypes(plantTypes);

        for (Plant plant : plants.getContent()) {
            LocationDto locationDto = locationService.getById(plant.getLocationId());
            PlantTypeDto plantTypeDto = mapPlantTypes.get(plant.getPlantTypeId());
            UserDto userDto = userService.getByIdUser(plant.getPicId());

            PlantDto plantDto = plantConverter.convert(plant);

            plantDto.setLocation(locationDto);
            plantDto.setPlantType(plantTypeDto);
            plantDto.setUser(userDto);
            plantDto.setCode(this.getCode(plant.getId().toString(), locationDto.getId(), plantTypeDto.getId()));

            resPlants.add(plantDto);
        }
        return new PageImpl<>(resPlants, plants.getPageable(), plants.getTotalElements());
    }

    @Override
    public Page<PlantDto> findByPlantTypePagination(String orgId, String plantType, Pageable pageable) {
        List<PlantDto> resPlants = new ArrayList<>();
        Page<Plant> plants = plantRepository.findByOrgIdEqualsAndPlantTypeId(new ObjectId(orgId) ,new ObjectId(plantType), pageable);
        List<ObjectId> locationIds = plants.stream().map(Plant::getLocationId)
                .collect(Collectors.toList());
        List<ObjectId> Users = plants.stream().map(Plant::getPicId)
                .collect(Collectors.toList());
        Map<ObjectId, LocationDto> mapLocations = locationService.getMapLocations(locationIds);
        Map<ObjectId, UserDto> mapUsers = userService.getMapUsers(Users);

        for (Plant plant : plants.getContent()) {
            LocationDto locationDto = mapLocations.get(plant.getLocationId());
            PlantTypeDto plantTypeDto = plantTypeService.getById(plant.getPlantTypeId());
            UserDto userDto = mapUsers.get(plant.getPicId());
            PlantDto plantDto = plantConverter.convert(plant);

            plantDto.setLocation(locationDto);
            plantDto.setPlantType(plantTypeDto);
            plantDto.setUser(userDto);
            plantDto.setCode(this.getCode(plant.getId().toString(), locationDto.getId(), plantTypeDto.getId()));

            resPlants.add(plantDto);
        }
        return new PageImpl<>(resPlants, plants.getPageable(), plants.getTotalElements());
    }

    @Override
    public Page<PlantDto> findPicPagination(String orgId, String pic, Pageable pageable) {
        List<PlantDto> resPlants = new ArrayList<>();
        Page<Plant> plants = plantRepository.findByOrgIdEqualsAndPicId(new ObjectId(orgId) ,new ObjectId(pic), pageable);
        List<ObjectId> locationIds = plants.stream().map(Plant::getLocationId)
                .collect(Collectors.toList());
        List<ObjectId> plantTypes = plants.stream().map(Plant::getPlantTypeId)
                .collect(Collectors.toList());
        Map<ObjectId, LocationDto> mapLocations = locationService.getMapLocations(locationIds);
        Map<ObjectId, PlantTypeDto> mapPlantTypes = plantTypeService.getMapPlantTypes(plantTypes);
        for (Plant plant : plants.getContent()) {
            LocationDto locationDto = mapLocations.get(plant.getLocationId());
            PlantTypeDto plantTypeDto = mapPlantTypes.get(plant.getPlantTypeId());
            UserDto userDto = userService.getByIdUser(plant.getPicId());

            PlantDto plantDto = plantConverter.convert(plant);

            plantDto.setLocation(locationDto);
            plantDto.setPlantType(plantTypeDto);
            plantDto.setUser(userDto);
            plantDto.setCode(this.getCode(plant.getId().toString(), locationDto.getId(), plantTypeDto.getId()));

            resPlants.add(plantDto);
        }
        return new PageImpl<>(resPlants, plants.getPageable(), plants.getTotalElements());
    }


    public static String getAlphaNumericString()
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

}
