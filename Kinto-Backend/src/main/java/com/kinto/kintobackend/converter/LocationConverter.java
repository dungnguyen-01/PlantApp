package com.kinto.kintobackend.converter;

import com.kinto.kintobackend.dto.LocationDto;
import com.kinto.kintobackend.model.Location;
import com.kinto.kintobackend.service.OrganizationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter {

    @Autowired
    OrganizationService organizationService;

    public Location convert(LocationDto locationDto) {
        // TODO
        Location location = new Location();
        location.setAddress(locationDto.getAddress());
        location.setOrgId(new ObjectId(locationDto.getOrganization().getId()));
        location.setStatus(locationDto.getStatus());
        location.setCode(locationDto.getCode());
        location.setCreateAt(locationDto.getCreateAt());
        location.setUpdateAt(locationDto.getUpdateAt());
        return location;
    }

    public LocationDto convert(Location location) {
        // TODO
        LocationDto  locationDto = new LocationDto();
        locationDto.setId(location.getId().toString());
        locationDto.setAddress(location.getAddress());
        locationDto.setStatus(location.getStatus());
        locationDto.setCode(location.getCode());
        locationDto.setCreateAt(location.getCreateAt());
        locationDto.setUpdateAt(location.getUpdateAt());
        return locationDto;
    }
}
