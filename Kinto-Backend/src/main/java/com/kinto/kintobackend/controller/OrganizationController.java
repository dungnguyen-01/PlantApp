package com.kinto.kintobackend.controller;

import com.kinto.kintobackend.dto.OrganizationDto;
import com.kinto.kintobackend.service.OrganizationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"https://app.kintovn.com","http://localhost:8080"})
@RequestMapping("/api/v1/organization")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/list")
    public ResponseEntity<?> getAll(){
        List<OrganizationDto> organizations = organizationService.getAll();
        return new ResponseEntity<>(organizations,HttpStatus.OK);
    }

    @GetMapping("/list-user")
    public ResponseEntity<?> getAllSelectByUser(){
        List<OrganizationDto> organizations = organizationService.getAllSelectByUser();
        return new ResponseEntity<>(organizations,HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> getAllOrganization(
            @RequestParam("page") Optional<Integer> pageOpt,
            @RequestParam("size") Optional<Integer> nOpt,
            @RequestParam("sortBy") Optional<String> sortOpt,
            @RequestParam("sortDir") Optional<Boolean> dirOpt){

        String property =  sortOpt.orElse("updateAt");
        Boolean direction = dirOpt.orElse(true);
        Sort sort = Sort.by(direction? Sort.Direction.DESC : Sort.Direction.ASC ,property);
        Pageable pageable =  PageRequest.of(pageOpt.orElse(0),nOpt.orElse(3),sort);

        Page<OrganizationDto> page = organizationService.findAllPagination(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> createTodo(@RequestBody OrganizationDto organizationDto){
        organizationService.create(organizationDto);
        return new ResponseEntity<>(organizationDto, HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> getByIdOrganization(@PathVariable("id") ObjectId id){
        return new ResponseEntity<>(organizationService.getByIdOrganization(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> updateById(@PathVariable("id") ObjectId id, @RequestBody OrganizationDto organizationDto){
        organizationService.update(id, organizationDto);
        return new ResponseEntity<>(organizationDto, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> deleteById(@PathVariable("id") ObjectId id){
        organizationService.deleteById(id);
        return new ResponseEntity<>("Delete success by id "+ id,HttpStatus.OK);
    }
}
