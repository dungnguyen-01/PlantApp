package com.kinto.kintobackend.security;

import com.kinto.kintobackend.dto.UserDto;
import com.kinto.kintobackend.model.User;
import com.kinto.kintobackend.repository.UserRepository;
import com.kinto.kintobackend.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            UserDto user = userService.getByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException(email);
            }
            return new UserDetailsImpl(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException(email+" User not found");
        }
    }
}
