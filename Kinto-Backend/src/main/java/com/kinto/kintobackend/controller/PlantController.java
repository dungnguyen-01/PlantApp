package com.kinto.kintobackend.controller;

import com.kinto.kintobackend.dto.PlantDto;
import com.kinto.kintobackend.repository.PlantRepository;
import com.kinto.kintobackend.service.PlantService;
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

import java.util.Optional;

@RestController
@CrossOrigin(origins = {"https://app.kintovn.com", "http://localhost:8080"})
@RequestMapping("/api/v1/plant")
public class PlantController {

    @Autowired
    PlantService plantService;
    @Autowired
    PlantRepository plantRepository;

    @GetMapping
    public ResponseEntity<?> getAllPlantPagination(
            @RequestParam("page") Optional<Integer> pageOpt,
            @RequestParam("plant_type") Optional<String> plantTypeOpt,
            @RequestParam("location") Optional<String> locationOpt,
            @RequestParam("pic") Optional<String> picOpt,
            @RequestParam("size") Optional<Integer> nOpt,
            @RequestParam("orgId") Optional<String> orgId,
            @RequestParam("sortBy") Optional<String> sortOpt,
            @RequestParam("sortDir") Optional<Boolean> dirOpt) {

        String property = sortOpt.orElse("updateAt");
        String plantType = plantTypeOpt.orElse("");
        String pic = picOpt.orElse("");
        String location = locationOpt.orElse("");
        Boolean direction = dirOpt.orElse(true);
        Sort sort = Sort.by(direction ? Sort.Direction.DESC : Sort.Direction.ASC, property);
        Pageable pageable = PageRequest.of(pageOpt.orElse(0), nOpt.orElse(15), sort);


        if (plantType.equals("Filter by plant type...")) plantType = "";
        if (pic.equals("Filter by user...")) pic = "";
        if (location.equals("Filter by location...")) location = "";

        Page<PlantDto> page;
        if (plantType != "" || location !="" || pic != "") {
            page = plantService.findFilterPagination(orgId.orElse(""), plantType, location, pic, pageable);
        } else {
            page = plantService.findAllPagination(orgId.orElse(""), pageable);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> create(@RequestBody PlantDto plantDto) {
        plantService.create(plantDto);
        return new ResponseEntity<>(plantDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByIdPlant(@PathVariable("id") ObjectId id) {
        return new ResponseEntity<>(plantService.getByIdPlant(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> updateById(@PathVariable("id") ObjectId id, @RequestBody PlantDto plantDto) {
        System.out.println(plantDto);
        return new ResponseEntity<>(plantDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> deleteById(@PathVariable("id") ObjectId id) {
        plantService.deleteById(id);
        return new ResponseEntity<>("Delete success by id " + id, HttpStatus.OK);
    }
}
