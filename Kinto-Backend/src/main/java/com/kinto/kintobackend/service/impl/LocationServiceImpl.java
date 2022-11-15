package com.kinto.kintobackend.service.impl;

import com.kinto.kintobackend.converter.LocationConverter;
import com.kinto.kintobackend.dto.LocationDto;
import com.kinto.kintobackend.exception.AlreadyExistsCollectionException;
import com.kinto.kintobackend.exception.ResourceNotFoundException;
import com.kinto.kintobackend.model.Location;
import com.kinto.kintobackend.repository.LocationRepository;
import com.kinto.kintobackend.service.LocationService;
import com.kinto.kintobackend.service.OrganizationService;
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
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    LocationConverter locationConverter;
    @Autowired
    OrganizationService organizationService;

    @Override
    public List<LocationDto> getAll(String orgId) {
        List<Location> locations = locationRepository.findByStatusIsTrueAndOrgIdEquals(new ObjectId(orgId));
        if (locations.size() > 0) {
            List<LocationDto> locationDtos = new ArrayList<>();
            locations.forEach(item -> {
                locationDtos.add(locationConverter.convert(item));
            });
            return locationDtos;
        }
        return new ArrayList<>();
    }

    @Override
    public Page<LocationDto> findAllPagination(String orgId, Pageable pageable) {
        Page<Location> locations = locationRepository.findAllByStatusIsTrueAndOrgIdEquals(new ObjectId(orgId), pageable);
        List<LocationDto> locationDtos = new ArrayList<>();
        for (Location location : locations) {
            locationDtos.add(locationConverter.convert(location));
        }
        return new PageImpl<>(locationDtos, locations.getPageable(), locations.getTotalElements());
    }

    @Override
    public void create(LocationDto locationDto) {
        Optional<Location> optionalLocation = locationRepository.findByAddress(locationDto.getAddress());
        if (!optionalLocation.isEmpty())
            throw new AlreadyExistsCollectionException("address: " + locationDto.getAddress() + " already exists");
        if (!checkLetter(locationDto.getCode()) == true)
            throw new ResourceNotFoundException("Enter 3 to 5 characters and numbers (A-Z, 0-9) without spaces and special characters.");
        if (locationRepository.existsByCode(locationDto.getCode()) == true)
            throw new AlreadyExistsCollectionException("Code: " + locationDto.getCode() + " already exits!");
        locationDto.setStatus(true);
        locationDto.setCreateAt(Instant.now());
        locationDto.setUpdateAt(Instant.now());
        locationRepository.save(locationConverter.convert(locationDto));
    }

    public static boolean checkLetter(String code) {
        boolean result = code.matches("^[A-Z0-9]*$");
        if (result == true && code != null || code.length() <= 2 || code.length() >= 6) return true;
        else return false;
    }

    @Override
    public Map<ObjectId, LocationDto> getMapLocations(List<ObjectId> locationIds) {
        List<Location> locations = locationRepository.findByIdIn(locationIds);
        return locations.stream()
                .collect(Collectors.toMap(Location::getId, l -> locationConverter.convert(l)));
    }

    @Override
    public LocationDto getById(ObjectId locationId) {
        Optional<Location> location = locationRepository.findById(locationId);
        if (location.isPresent()) {
            LocationDto locationDto = locationConverter.convert(location.get());
            locationDto.setOrganization(organizationService.getByIdOrganization(location.get().getOrgId()));
            return locationDto;
        } else throw new ResourceNotFoundException(locationId + " not found!");
    }


    @Override
    public void update(ObjectId id, LocationDto locationDto) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        Optional<Location> locationSameName = locationRepository.findByAddress(locationDto.getAddress());
        Optional<Location> locationSameCode = locationRepository.findByCode(locationDto.getCode());

        if (!checkLetter(locationDto.getCode()) == true)
            throw new ResourceNotFoundException("Enter 3 to 5 characters and numbers (A-Z, 0-9) without spaces and special characters.");
        if (locationSameCode.isPresent() && !locationSameCode.get().getId().equals(id))
            throw new AlreadyExistsCollectionException("Code: "+locationDto.getCode() + " already exists");
        if (!locationOptional.isPresent()) throw new ResourceNotFoundException(id + " not found!");
        if (locationSameName.isPresent() && !locationSameName.get().getId().equals(id))
            throw new AlreadyExistsCollectionException("Name: "+locationDto.getAddress() + " already exists");
        System.out.println(locationDto);

        Location locationUpdate = locationOptional.get();
        locationUpdate.setStatus(true);
        locationUpdate.setCode(locationDto.getCode());
        locationUpdate.setAddress(locationDto.getAddress());
        locationUpdate.setOrgId(new ObjectId(locationDto.getOrganization().getId()));
        locationUpdate.setUpdateAt(Instant.now());
        locationRepository.save(locationUpdate);
    }


    @Override
    public void deleteById(ObjectId id) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        if (locationOptional.isPresent()) {
            locationOptional.get().setStatus(false);
            locationRepository.save(locationOptional.get());
        } else throw new ResourceNotFoundException(id + " not found!");
    }


}
