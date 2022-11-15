package com.kinto.kintobackend.service;

import com.kinto.kintobackend.api.AuthRequest;
import com.kinto.kintobackend.dto.RegisterDto;
import com.kinto.kintobackend.dto.ResetPassDto;
import com.kinto.kintobackend.dto.UserDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserDto> getAllUser(String orgId);

    void create(UserDto userDto);

    UserDto getByIdUser(ObjectId id);

    void update(ObjectId id, UserDto userDto);

    void deleteById(ObjectId id);

    Page<UserDto> findAllPagination(Pageable pageable);

    Map<ObjectId, UserDto> getMapUsers(List<ObjectId> userIds);

    void register(RegisterDto registerDto);

    UserDto getByEmail(String email);

    void forgotPassword(String email);

    void resetPassword(ResetPassDto resetPassDto);

    void createUserLoginWithGoogle(String email, String fullname, String password);

    boolean findByEmail(AuthRequest authRequest);

    void changePassword(String id, String oldPass, String newPass, String confirmPass);

    UserDto getByIdAll(ObjectId id);

    UserDto updateORG(String id, String orgId);
}
