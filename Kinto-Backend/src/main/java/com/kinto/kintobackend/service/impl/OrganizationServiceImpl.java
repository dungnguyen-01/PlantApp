package com.kinto.kintobackend.service.impl;

import com.kinto.kintobackend.converter.OrganizationConverter;
import com.kinto.kintobackend.dto.OrganizationDto;
import com.kinto.kintobackend.exception.AlreadyExistsCollectionException;
import com.kinto.kintobackend.exception.ResourceNotFoundException;
import com.kinto.kintobackend.model.Organization;
import com.kinto.kintobackend.model.Role;
import com.kinto.kintobackend.repository.OrganizationRepository;
import com.kinto.kintobackend.service.OrganizationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    OrganizationConverter organizationConverter;

    @EventListener(ApplicationReadyEvent.class)
    public void CreateORGAfterStartup() {
        Optional<Organization> organizationOptional = organizationRepository.findByName("KintoVN");
        if (!organizationOptional.isPresent()){
            Organization organization = new Organization();
            organization.setName("KintoVN");
            organization.setCreateAt(Instant.now());
            organization.setUpdateAt(Instant.now());
            organization.setStatus(true);
            organizationRepository.save(organization);
        }
    }
    @Override
    public List<OrganizationDto> getAll() {
        List<Organization> organizations = organizationRepository.findAllByNameIsNotLikeAndStatusIsTrue("KintoVN");

        if (organizations.size() > 0) {
            List<OrganizationDto> organizationDtos = new ArrayList<>();
            organizations.forEach(item ->{
                organizationDtos.add(organizationConverter.convert(item));
            });
            return organizationDtos;
        }
        else return new ArrayList<>();

    }

    @Override
    public Page<OrganizationDto> findAllPagination(Pageable pageable) {
        Page<Organization> organizations = organizationRepository.findAllByStatusIsTrue(pageable);

        if (organizations.getSize() > 0){
            List<OrganizationDto> organizationDtos = new ArrayList<>();

            organizations.getContent().forEach(item -> {
                organizationDtos.add(organizationConverter.convert(item));
            });
            return new PageImpl<>(organizationDtos, organizations.getPageable(), organizations.getTotalElements());
        }
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public Map<ObjectId, OrganizationDto> getMapOrganization(List<ObjectId> organizationIds) {
        List<Organization> organizations = organizationRepository.findByIdIn(organizationIds);
        return organizations.stream().collect(Collectors.toMap(Organization::getId, o -> organizationConverter.convert(o)));
    }

    @Override
    public Organization getOrganizationByName(String kinto) {
        Optional<Organization> organizationOptional = organizationRepository.findByName(kinto);
        System.out.println(organizationOptional);
        if (organizationOptional.isPresent()) return organizationOptional.get();
        else return null;
    }

    @Override
    public List<OrganizationDto> getAllSelectByUser() {
        List<Organization> organizations = organizationRepository.findAllByStatusIsTrue();

        if (organizations.size() > 0) {
            List<OrganizationDto> organizationDtos = new ArrayList<>();
            organizations.forEach(item ->{
                organizationDtos.add(organizationConverter.convert(item));
            });
            return organizationDtos;
        }
        else return new ArrayList<>();

    }

    @Override
    public void create(OrganizationDto organizationDto) {
        Optional<Organization> organizationOptional = organizationRepository.findByName(organizationDto.getName());
        if (!organizationOptional.isPresent()) {
            Organization organization = organizationConverter.convert(organizationDto);
            organization.setCreateAt(Instant.now());
            organization.setUpdateAt(Instant.now());
            organization.setStatus(true);
            organizationRepository.save(organization);
        }else throw new AlreadyExistsCollectionException(organizationDto.getName() + " already exists");
    }

    @Override
    public OrganizationDto getByIdOrganization(ObjectId id) {
        Optional<Organization> organizationOptional = organizationRepository.findById(id);
        if (organizationOptional.isPresent()) return organizationConverter.convert(organizationOptional.get());
        else throw new ResourceNotFoundException(id + " not found!");
    }

    @Override
    public void update(ObjectId id, OrganizationDto organizationDto){
        Optional<Organization> organizationOptional = organizationRepository.findById(id);
        Optional<Organization> organizationSameName = organizationRepository.findByName(organizationDto.getName());
        if (organizationOptional.isPresent()){
            if (organizationSameName.isPresent() && !organizationSameName.get().getId().equals(id))
                throw new AlreadyExistsCollectionException(organizationDto.getName() + " already exists");
            Organization organizationUpdate = organizationOptional.get();
            organizationUpdate.setName(organizationDto.getName());
            organizationUpdate.setStatus(true);
            organizationUpdate.setUpdateAt(Instant.now());
            organizationRepository.save(organizationUpdate);
        }else throw new ResourceNotFoundException(id + " not found!");
    }

    @Override
    public void deleteById(ObjectId id){
        Optional<Organization> organizationOptional = organizationRepository.findById(id);
        if (organizationOptional.isPresent()){
            if (organizationOptional.get().getName().equals("KintoVN") == true) {
                System.out.println("Don't delete by id: "+id);
            } else {
                organizationOptional.get().setStatus(false);
                organizationRepository.save(organizationOptional.get());
            }
        }else throw new ResourceNotFoundException(id + " not found!");
    }

    @Override
    public OrganizationDto getById(ObjectId orgId) {
        Optional<Organization> organization = organizationRepository.findById(orgId);
        if (organization.isPresent()) return organizationConverter.convert(organization.get());
        else throw new ResourceNotFoundException(orgId+" not found!");
    }


}
