package com.kinto.kintobackend.controller;

import com.kinto.kintobackend.dto.RoleDto;
import com.kinto.kintobackend.service.RoleService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"https://app.kintovn.com","http://localhost:8080"})
@RequestMapping("/api/v1/role")
public class RoleController {
    @Autowired
    RoleService  roleService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllRole() {
        List<RoleDto> roleDtoList = roleService.getAllUser();
        return new ResponseEntity<>(roleDtoList, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable("id") ObjectId id) {
        return new ResponseEntity<>(roleService.getRoleById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> create(@RequestBody RoleDto roleDto) {
        roleService.create(roleDto);
        System.out.println(roleDto);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> update(@PathVariable("id") ObjectId id, @RequestBody RoleDto roleDto) {
        roleService.update(id,roleDto);
        //System.out.println(roleDto);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> update(@PathVariable("id") ObjectId id) {
        roleService.deleteById(id);
        return new ResponseEntity<>("Delete successfully by id: "+ id, HttpStatus.OK);
    }
}
