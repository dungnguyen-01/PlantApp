package com.kinto.kintobackend.controller;


import com.kinto.kintobackend.dto.LocationDto;
import com.kinto.kintobackend.service.LocationService;
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

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"https://app.kintovn.com","http://localhost:8080"})
@RequestMapping("/api/v1/location")
public class LocationController {

    @Autowired
    LocationService locationService;

    @GetMapping ("/list")
    public ResponseEntity<?> getAll(@RequestParam("orgId") Optional<String> orgId){
        List<LocationDto> locationDtos =locationService.getAll(orgId.orElse(""));
        return new ResponseEntity<>(locationDtos,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllLocation(@RequestParam("page")
            Optional<Integer> pageOpt,
            @RequestParam("size") Optional<Integer> nOpt,
            @RequestParam("sortBy") Optional<String> sortOpt,
            @RequestParam("orgId") Optional<String> orgId,
            @RequestParam("sortDir") Optional<Boolean> dirOpt){

        String property =  sortOpt.orElse("updateAt");
        Boolean direction = dirOpt.orElse(true);
        Sort sort = Sort.by(direction? Sort.Direction.DESC : Sort.Direction.ASC ,property);
        Pageable pageable =  PageRequest.of(pageOpt.orElse(0),nOpt.orElse(15),sort);

        Page<LocationDto> page = locationService.findAllPagination(orgId.orElse(""), pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> create(@RequestBody LocationDto locationDto){
        locationService.create(locationDto);
        return new ResponseEntity<>(locationDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id")ObjectId id){
        return new ResponseEntity<>(locationService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> update(@PathVariable("id") ObjectId id, @RequestBody LocationDto locationDto){
        locationService.update(id,locationDto);
        return new ResponseEntity<>(locationDto, HttpStatus.OK);
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> deleteById(@PathVariable("id") ObjectId id) {
        locationService.deleteById(id);
        return new ResponseEntity<>("Delete success by id "+ id,HttpStatus.OK);
    }
}
