package com.kinto.kintobackend.service.impl;


import com.kinto.kintobackend.api.AuthRequest;
import com.kinto.kintobackend.converter.RoleConverter;
import com.kinto.kintobackend.converter.UserConverter;
import com.kinto.kintobackend.dto.*;
import com.kinto.kintobackend.exception.AlreadyExistsCollectionException;
import com.kinto.kintobackend.exception.ResourceNotFoundException;
import com.kinto.kintobackend.exception.ResponseErrorException;
import com.kinto.kintobackend.model.Organization;
import com.kinto.kintobackend.model.Role;
import com.kinto.kintobackend.model.User;
import com.kinto.kintobackend.repository.OrganizationRepository;
import com.kinto.kintobackend.repository.UserRepository;
import com.kinto.kintobackend.service.OrganizationService;
import com.kinto.kintobackend.service.RoleService;
import com.kinto.kintobackend.service.UserService;
import com.kinto.kintobackend.utils.mail.MailService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    UserConverter userConverter;
    @Autowired
    OrganizationService organizationService;
    @Autowired
    RoleService roleService;
    @Autowired
    RoleConverter roleConverter;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void CreateORGAfterStartup() {
        Optional<User> userOptionalCheckEmail = userRepository.findByEmailAndStatusIsTrue("info.kintovn@gmail.com");
        if (!userOptionalCheckEmail.isPresent()) {
            Role role = roleService.getRoleByUser("Admin");
            Organization organization = organizationService.getOrganizationByName("KintoVN");
            User user = new User();
            user.setRoleId(role.getId());
            user.setEmail("info.kintovn@gmail.com");
            user.setFullname("Kinto VN");
            user.setOrgId(organization.getId());
            user.setCreateAt(Instant.now());
            user.setUpdateAt(Instant.now());
            user.setStatus(true);
            user.setPassword(passwordEncoder.encode("kintovn@123"));
            userRepository.save(user);
        }
    }


    @Override
    public List<UserDto> getAllUser(String orgId) {
        List<User> users = userRepository.findByStatusIsTrueAndOrgIdEquals(new ObjectId(orgId));
        List<ObjectId> organizationIds = users.stream().map(User::getOrgId)
                .collect(Collectors.toList());
        List<ObjectId> roleIds = users.stream().map(User::getRoleId)
                .collect(Collectors.toList());

        Map<ObjectId, OrganizationDto> mapOrganizations = organizationService.getMapOrganization(organizationIds);
        Map<ObjectId, RoleDto> mapRoles = roleService.getMapRole(roleIds);

        if (users.size() > 0) {
            List<UserDto> userDtos = new ArrayList<>();
            users.forEach(item -> {
                OrganizationDto organizationDto = mapOrganizations.get(item.getOrgId());
                RoleDto roleDto = mapRoles.get(item.getRoleId());

                UserDto userDto = userConverter.convert(item);
                userDto.setRole(roleDto);
                userDto.setOrganization(organizationDto);
                userDto.setEmail("");
                userDto.setPassword("");
                userDtos.add(userDto);
            });
            return userDtos.stream()
                    .filter(item -> item.getRole().getRoleName().equals("Admin") || item.getRole().getRoleName().equals("Editor"))
                            .collect(Collectors.toList());
        } else return new ArrayList<>();
    }

    @Override
    public void create(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmailAndStatusIsTrue(userDto.getEmail());
        if (!optionalUser.isPresent()) {
            User user = new User();
            user.setFullname(userDto.getFullname());
            user.setRoleId(new ObjectId(userDto.getRole().getId()));
            user.setEmail(userDto.getEmail());
            user.setOrgId(new ObjectId(userDto.getOrganization().getId()));
            user.setPassword(userDto.getPassword());
            user.setStatus(true);
            user.setUpdateAt(Instant.now());
            user.setCreateAt(Instant.now());
            userRepository.save(user);
        } else throw new AlreadyExistsCollectionException(userDto.getEmail() + " already exists");
    }

    @Override
    public UserDto getByIdUser(ObjectId id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            OrganizationDto organization = organizationService.getById(userOptional.get().getOrgId());
            RoleDto roleDto = roleService.getRoleById(userOptional.get().getRoleId());
            UserDto userDto = userConverter.convert(userOptional.get());
            userDto.setOrganization(organization);
            userDto.setRole(roleDto);
            return userDto;
        } else throw new ResourceNotFoundException(id + " not found!");
    }

    public UserDto getById(ObjectId id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            OrganizationDto organization = organizationService.getById(userOptional.get().getOrgId());
            RoleDto roleDto = roleService.getRoleById(userOptional.get().getRoleId());
            UserDto userDto = userConverter.convert(userOptional.get());
            userDto.setOrganization(organization);
            userDto.setRole(roleDto);
            return userDto;
        } else throw new ResourceNotFoundException(id + " not found!");
    }

    @Override
    public void update(ObjectId id, UserDto userDto) {
        Optional<User> userOptionalFindbyId = userRepository.findById(id);
        Optional<User> userOptionalCheckEmail = userRepository.findByEmailAndStatusIsTrue(userDto.getEmail());
        if (userOptionalFindbyId.isPresent()) {
            if (userOptionalCheckEmail.isPresent() && userOptionalCheckEmail.get().getEmail().equals(userDto.getEmail())) {
                User userUpdate = userOptionalFindbyId.get();
                userUpdate.setUpdateAt(Instant.now());
                userUpdate.setEmail(userDto.getEmail());
                userUpdate.setFullname(userDto.getFullname());
                userUpdate.setRoleId(new ObjectId(userDto.getRole().getId()));
                userUpdate.setOrgId(new ObjectId(userDto.getOrganization().getId()));
                userUpdate.setStatus(true);
                userRepository.save(userUpdate);
            } else throw new AlreadyExistsCollectionException(userDto.getEmail() + " already exists");
        } else throw new ResourceNotFoundException(id + " not found!");
    }

    @Override
    public void deleteById(ObjectId id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userOptional.get().setStatus(false);
            userRepository.save(userOptional.get());
        }
        else throw new ResourceNotFoundException(id + " not found!");
    }

    @Override
    public Page<UserDto> findAllPagination(Pageable pageable) {
        Page<User> users = userRepository.findAllByStatusIsTrue(pageable);

        List<ObjectId> organizationIds = users.stream().map(User::getOrgId)
                .collect(Collectors.toList());
        List<ObjectId> roleIds = users.stream().map(User::getRoleId)
                .collect(Collectors.toList());

        Map<ObjectId, OrganizationDto> mapOrganizations = organizationService.getMapOrganization(organizationIds);
        Map<ObjectId, RoleDto> mapRoles = roleService.getMapRole(roleIds);

        List<UserDto> userDtos = new ArrayList<>();

        users.forEach(item -> {
            OrganizationDto organizationDto = mapOrganizations.get(item.getOrgId());
            RoleDto roleDto = mapRoles.get(item.getRoleId());

            UserDto userDto = userConverter.convert(item);
            userDto.setRole(roleDto);
            userDto.setOrganization(organizationDto);
            userDtos.add(userDto);
        });

        return new PageImpl<>(userDtos, users.getPageable(), users.getTotalElements());
    }

    @Override
    public Map<ObjectId, UserDto> getMapUsers(List<ObjectId> userIds) {
        List<User> users = userRepository.findByIdIn(userIds);
        return users.stream()
                .collect(Collectors.toMap(User::getId, l -> userConverter.convert(l)));
    }

    @Override
    public void register(RegisterDto registerDto) {
        Optional<User> userOptionalCheckEmail = userRepository.findByEmailAndStatusIsTrue(registerDto.getEmail());
        if (!userOptionalCheckEmail.isPresent()) {
            Role role = roleService.getRoleByUser("Viewer");
            OrganizationDto organization = organizationService.getByIdOrganization(new ObjectId(registerDto.getOrgId()));
            User user = new User();
            user.setRoleId(role.getId());
            user.setEmail(registerDto.getEmail());
            user.setFullname(registerDto.getFullname());
            user.setOrgId(new ObjectId(organization.getId()));
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setStatus(true);
            user.setCreateAt(Instant.now());
            user.setUpdateAt(Instant.now());
            userRepository.save(user);
            System.out.println(user);
        } else throw new AlreadyExistsCollectionException(registerDto.getEmail() + " already exits!");
    }

    @Override
    public UserDto getByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmailAndStatusIsTrue(email);
        return this.getById(userOptional.get().getId());
    }

    @Autowired
    MailService mailService;

    @Override
    public void forgotPassword(String email) {
        Optional<User> user = userRepository.findByEmailAndStatusIsTrue(email);
        if (user.isPresent()) {
            String token = Integer.toHexString(user.get().getPassword().hashCode());
            mailService.sendPasswordToken(token, email);
        } else throw new ResourceNotFoundException(email + " not found!");
    }

    @Override
    public void resetPassword(ResetPassDto resetPassDto) {
        if (!resetPassDto.getPassword().equals(resetPassDto.getConfirm()))
            throw new ResourceNotFoundException("Password and Confirm Password need in the same!");
        if (!userRepository.existsByEmail(resetPassDto.getEmail())) throw new ResourceNotFoundException(resetPassDto.getEmail() + " not found!");

        Optional<User> user = userRepository.findByEmailAndStatusIsTrue(resetPassDto.getEmail());
        String token = Integer.toHexString(user.get().getPassword().hashCode());
        if (!token.equals(resetPassDto.getToken())) throw new ResourceNotFoundException("Token not correct!");

        User userUpdate = user.get();
        userUpdate.setPassword(passwordEncoder.encode(resetPassDto.getPassword()));
        userUpdate.setUpdateAt(Instant.now());
        userUpdate.setStatus(true);
        userRepository.save(userUpdate);
    }

    @Override
    public void createUserLoginWithGoogle(String email, String fullname, String password) {
        Optional<User> userOptionalCheckEmail = userRepository.findByEmailAndStatusIsTrue(email);
        if (!userOptionalCheckEmail.isPresent()) {
            Role role = roleService.getRoleByUser("Viewer");
            Organization organization = organizationService.getOrganizationByName("KintoVN");
            User user = new User();
            user.setEmail(email);
            user.setFullname(fullname);
            user.setRoleId(role.getId());
            user.setOrgId(organization.getId());
            user.setCreateAt(Instant.now());
            user.setUpdateAt(Instant.now());
            user.setStatus(true);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        } else {
            User user = userOptionalCheckEmail.get();
            user.setPassword(passwordEncoder.encode(password));
            user.setUpdateAt(Instant.now());
            userRepository.save(user);
        }
    }

    @Override
    public boolean findByEmail(AuthRequest authRequest) {
        Optional<User> userOptional = userRepository.findByEmailAndStatusIsTrue(authRequest.getEmail());
        if (userOptional.isPresent()) {
            if (passwordEncoder.matches(authRequest.getPassword(), userOptional.get().getPassword())==true) return true;
            else return false;
        }
        else return false;
    }

    @Override
    public void changePassword(String id, String oldPass, String newPass, String confirmPass) {
        if (!newPass.equals(confirmPass)) throw new ResponseErrorException("New password and confirm password need in the same!");
        else {
            Optional<User> user = userRepository.findById(new ObjectId(id));
            if (user.isPresent()) {
                if (passwordEncoder.matches(oldPass, user.get().getPassword()) == true) {
                    user.get().setPassword(passwordEncoder.encode(newPass));
                    userRepository.save(user.get());
                } else throw new ResourceNotFoundException("Wrong password!");
            } else throw new ResourceNotFoundException("Account not found!");
        }
    }

    @Override
    public UserDto getByIdAll(ObjectId id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            RoleDto roleDto = roleService.getRoleById(userOptional.get().getRoleId());
            UserDto userDto = userConverter.convert(userOptional.get());
            userDto.setRole(roleDto);
            userDto.setEmail("");
            userDto.setPassword("");
            return userDto;
        } else throw new ResourceNotFoundException(id + " not found!");
    }

    @Override
    public UserDto updateORG(String id, String orgId) {
        Optional<User> userOptional = userRepository.findByIdAndStatusIsTrue(new ObjectId(id));
        Optional<Organization> organizationOptional = organizationRepository.findByIdAndStatusIsTrue(new ObjectId(orgId));
        if (userOptional.isEmpty()) throw new ResourceNotFoundException("User not found!");
        if (organizationOptional.isEmpty()) throw new ResourceNotFoundException("Organization not found!");
        userOptional.get().setOrgId(organizationOptional.get().getId());
        userOptional.get().setUpdateAt(Instant.now());
        userRepository.save(userOptional.get());
        return userConverter.convert(userOptional.get());
    }
}
