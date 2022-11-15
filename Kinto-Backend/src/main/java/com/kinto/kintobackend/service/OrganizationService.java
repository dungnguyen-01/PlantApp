package com.kinto.kintobackend.service;


import com.kinto.kintobackend.dto.OrganizationDto;
import com.kinto.kintobackend.model.Organization;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface OrganizationService {
    List<OrganizationDto> getAll();

    void create( OrganizationDto organizationDto);

    OrganizationDto getByIdOrganization(ObjectId id);

    void update(ObjectId id,  OrganizationDto organizationDto);

    void deleteById(ObjectId id);

    OrganizationDto getById(ObjectId orgId);

    Page<OrganizationDto> findAllPagination(Pageable pageable);

    Map<ObjectId, OrganizationDto> getMapOrganization(List<ObjectId> organizationIds);

    Organization getOrganizationByName(String kinto);

    List<OrganizationDto> getAllSelectByUser();
}
