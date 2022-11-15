package com.kinto.kintobackend.controller;

import com.kinto.kintobackend.api.AuthRequest;
import com.kinto.kintobackend.api.AuthResponse;
import com.kinto.kintobackend.dto.RegisterDto;
import com.kinto.kintobackend.dto.ResetPassDto;
import com.kinto.kintobackend.dto.UserDto;
import com.kinto.kintobackend.exception.ResourceNotFoundException;
import com.kinto.kintobackend.jwt.JwtUtils;
import com.kinto.kintobackend.security.UserDetailsImpl;
import com.kinto.kintobackend.service.RoleService;
import com.kinto.kintobackend.service.UserService;
import com.kinto.kintobackend.utils.mail.MailService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"https://app.kintovn.com", "http://localhost:8080"})
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    HttpServletRequest request;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_Admin','ROLE_Editor','ROLE_Viewer')")
    public ResponseEntity<?> getAll(@RequestParam("orgId") Optional<String> orgId) {
        List<UserDto> userDtos = userService.getAllUser(orgId.orElse(""));
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> getAllUser(@RequestParam("page")
                                                Optional<Integer> pageOpt,
                                        @RequestParam("size") Optional<Integer> nOpt,
                                        @RequestParam("sortBy") Optional<String> sortOpt,
                                        @RequestParam("sortDir") Optional<Boolean> dirOpt) {
            String property = sortOpt.orElse("updateAt");
            Boolean direction = dirOpt.orElse(true);
            Sort sort = Sort.by(direction ? Sort.Direction.DESC : Sort.Direction.ASC, property);
            Pageable pageable = PageRequest.of(pageOpt.orElse(0), nOpt.orElse(5), sort);

            Page<UserDto> page = userService.findAllPagination(pageable);
            return new ResponseEntity<>(page, HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        System.out.println("this is a user: " + userDto);
        userService.create(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> getByIdUser(@PathVariable("id") ObjectId id) {
        return new ResponseEntity<>(userService.getByIdUser(id), HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    @PreAuthorize("hasAnyRole('ROLE_Admin','ROLE_Editor','ROLE_Viewer')")
    public ResponseEntity<?> getByIdAll(@PathVariable("id") ObjectId id) {
        return new ResponseEntity<>(userService.getByIdAll(id), HttpStatus.OK);
    }
    //update , pic, create

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> updateById(@PathVariable("id") ObjectId id, @RequestBody UserDto userDto) {
        userService.update(id, userDto);
        System.out.println(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> deleteById(@PathVariable("id") ObjectId id) {
        userService.deleteById(id);
        return new ResponseEntity<>("Delete success by id " + id, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        System.out.println("this is a user: " + registerDto);
        userService.register(registerDto);
        return new ResponseEntity<>(registerDto, HttpStatus.OK);
    }

    @PutMapping("/update-org")
    public ResponseEntity<?> updateORG(@RequestParam("id") String id,
                                        @RequestParam("orgId") String orgId) {
        return new ResponseEntity<>(userService.updateORG(id, orgId), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
        if (userService.findByEmail(authRequest) == false) {
            throw new ResourceNotFoundException("Incorrect account and password");
        } else {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            AuthResponse authResponse = new AuthResponse();
            authResponse.setEmail(userDetails.getUser().getEmail());
            authResponse.setAccessToken(jwt);
            authResponse.setRole(userDetails.getUser().getRole().getRoleName());
            authResponse.setUser(userService.getByIdUser(new ObjectId(userDetails.getUser().getId())));
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        }

    }

    // for got password
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgot(@RequestBody AuthRequest authRequest) {
        userService.forgotPassword(authRequest.getEmail());
        return ResponseEntity.ok("thanh cong");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> reset_password(@RequestBody ResetPassDto resetPassDto) {
        userService.resetPassword(resetPassDto);
        return ResponseEntity.ok("thanh cong");
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasAnyRole('ROLE_Admin','ROLE_Editor','ROLE_Viewer')")
    public ResponseEntity<?> change_password(@RequestParam("new-pass") String newPass,
                                             @RequestParam("old-pass") String oldPass,
                                             @RequestParam("confirm-pass") String confirmPass,
                                             @RequestParam("id") String id
                                             ) {
        System.out.println(id);
        userService.changePassword(id ,oldPass, newPass, confirmPass);
        return ResponseEntity.ok("thanh cong");
    }

    @PostMapping("/google-login")
    public ResponseEntity<?> googleAuth(@RequestParam("email") String email,
                                        @RequestParam("fullname") String fullname,
                                        @RequestParam("password") String password) {
        userService.createUserLoginWithGoogle(email, fullname, password);

        UserDto userDto = userService.getByEmail(email);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        AuthResponse authResponse = new AuthResponse();
        authResponse.setEmail(userDetails.getUser().getEmail());
        authResponse.setAccessToken(jwt);
        authResponse.setRole(userDetails.getUser().getRole().getRoleName());
        authResponse.setUser(userService.getByIdUser(new ObjectId(userDetails.getUser().getId())));
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
