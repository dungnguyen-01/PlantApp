package com.kinto.kintobackend.service.impl;

import com.kinto.kintobackend.converter.RoleConverter;
import com.kinto.kintobackend.dto.RoleDto;
import com.kinto.kintobackend.exception.AlreadyExistsCollectionException;
import com.kinto.kintobackend.exception.ResourceNotFoundException;
import com.kinto.kintobackend.model.Organization;
import com.kinto.kintobackend.model.Role;
import com.kinto.kintobackend.repository.RoleRepository;
import com.kinto.kintobackend.service.RoleService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleConverter roleConverter;

    @Override
    public RoleDto getById(ObjectId roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()) return roleConverter.convert(role.get());
        else return null;
    }
    @Override
    public List<RoleDto> getAllUser() {
        List<Role> roles = roleRepository.findAll();
        if (roles.size() > 0 ) {
            List<RoleDto> roleDtos = new ArrayList<>();
            roles.forEach(item -> {
                roleDtos.add(roleConverter.convert(item));
            });
            return roleDtos;
        }else return new ArrayList<>();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void CreateORGAfterStartup() {
        Role admin = new Role();
        admin.setRoleName("Admin");
        Role editor = new Role();
        editor.setRoleName("Editor");
        Role viewer = new Role();
        viewer.setRoleName("Viewer");

        List<Role> roles = new ArrayList<>();
        roles.add(admin);
        roles.add(editor);
        roles.add(viewer);
        roles.forEach(item -> {
            Optional<Role> roleOptional = roleRepository.findByRoleName(item.getRoleName());
            if (!roleOptional.isPresent()){
               roleRepository.save(item);
            }
        });
    }

    @Override
    public void create(RoleDto roleDto) {
        Optional<Role> roleOptional = roleRepository.findByRoleName(roleDto.getRoleName());
        if(!roleOptional.isPresent()) {
            Role role = new Role();
            role.setRoleName(roleDto.getRoleName());
            roleRepository.save(role);
        } else throw new AlreadyExistsCollectionException(roleDto.getRoleName() + " already exits!");
    }
    @Override
    public void update(ObjectId id, RoleDto roleDto) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            Role role = new Role();
            role.setId(id);
            role.setRoleName(roleDto.getRoleName());
            role.setUpdateAt(Instant.now());
            roleRepository.save(role);
        } else throw new ResourceNotFoundException(id+ " not found!");
    }

    @Override
    public void deleteById(ObjectId id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDto getRoleById(ObjectId id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            return roleConverter.convert(roleOptional.get());
        } else throw new ResourceNotFoundException(id+ " not found!");
    }

    @Override
    public Map<ObjectId, RoleDto> getMapRole(List<ObjectId> roleIds) {
        List<Role> roles = roleRepository.findByIdIn(roleIds);
        return roles.stream().collect(Collectors.toMap(Role::getId, r -> roleConverter.convert(r)));
    }

    @Override
    public Role getRoleByUser(String name) {
        Optional<Role> role = roleRepository.findByRoleName(name);
        System.out.println(role);
        if (role.isPresent()) return role.get();
        else return null;
    }


}
