package com.kinto.kintobackend.service;

import com.kinto.kintobackend.dto.RoleDto;
import com.kinto.kintobackend.model.Role;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public interface RoleService {
    RoleDto getById(ObjectId roleId);

    List<RoleDto> getAllUser();

    void create(RoleDto roleDto);

    RoleDto getRoleById(ObjectId id);

    Map<ObjectId, RoleDto> getMapRole(List<ObjectId> roleIds);

    Role getRoleByUser(String name);

    void update(ObjectId id, RoleDto roleDto);

    void deleteById(ObjectId id);
}
