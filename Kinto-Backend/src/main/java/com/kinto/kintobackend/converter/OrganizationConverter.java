package com.kinto.kintobackend.converter;


import com.kinto.kintobackend.dto.OrganizationDto;
import com.kinto.kintobackend.model.Organization;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrganizationConverter {

    public Organization convert(OrganizationDto organizationDto){
        Organization organization = new Organization();
        organization.setName(organizationDto.getName());
        organization.setStatus(organizationDto.getStatus());
        organization.setUpdateAt(organizationDto.getUpdateAt());
        organization.setCreateAt(organizationDto.getCreateAt());
        return organization;
    }

    public OrganizationDto convert(Organization organization){
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(organization.getId().toString());
        organizationDto.setName(organization.getName());
        organizationDto.setStatus(organization.getStatus());
        organizationDto.setUpdateAt(organization.getUpdateAt());
        organizationDto.setCreateAt(organization.getCreateAt());

        return organizationDto;
    }

}
