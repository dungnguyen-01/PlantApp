package com.kinto.kintobackend.converter;


import com.kinto.kintobackend.dto.UserDto;
import com.kinto.kintobackend.model.User;
import com.kinto.kintobackend.service.OrganizationService;
import com.kinto.kintobackend.service.RoleService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    @Autowired
    OrganizationService organizationService;
    @Autowired
    RoleService roleService;

    public  User convert(UserDto userDto){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFullname(userDto.getFullname());
        user.setOrgId(new ObjectId(userDto.getOrganization().getId()));
        user.setStatus(userDto.getStatus());
        user.setUpdateAt(userDto.getUpdateAt());
        user.setCreateAt(userDto.getCreateAt());

        return user;
    }

    public  UserDto convert(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId().toString());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setFullname(user.getFullname());
        userDto.setStatus(user.getStatus());
        userDto.setUpdateAt(user.getUpdateAt());
        userDto.setCreateAt(user.getCreateAt());

        return userDto;
    }
}
