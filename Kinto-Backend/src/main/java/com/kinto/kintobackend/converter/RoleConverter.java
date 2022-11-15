package com.kinto.kintobackend.converter;


import com.kinto.kintobackend.dto.RoleDto;
import com.kinto.kintobackend.model.Role;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;


@Component
public class RoleConverter {

    public Role convert(RoleDto roleDto) {
        Role role = new Role();
        role.setId(new ObjectId(roleDto.getId()));
        role.setRoleName(roleDto.getRoleName());
        return role;
    }

    public RoleDto convert(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId().toString());
        roleDto.setRoleName(role.getRoleName());
        return roleDto;
    }
}
