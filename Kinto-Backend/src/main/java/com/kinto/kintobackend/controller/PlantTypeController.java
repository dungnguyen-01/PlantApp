package com.kinto.kintobackend.controller;

import com.kinto.kintobackend.dto.PlantTypeDto;
import com.kinto.kintobackend.model.PlantType;
import com.kinto.kintobackend.service.PlantTypeService;
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
@RequestMapping("/api/v1/plant-type")
public class PlantTypeController {

    @Autowired
    PlantTypeService plantTypeService;
    @Autowired
    HttpServletRequest request;

    @GetMapping ("/list")
    public ResponseEntity<?> getAll(@RequestParam("orgId") Optional<String> orgId){
        List<PlantTypeDto> plantTypeDtos =plantTypeService.getAll(orgId.orElse(""));
        return new ResponseEntity<>(plantTypeDtos,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllPlantType(
             @RequestParam("page") Optional<Integer> pageOpt,
             @RequestParam("size") Optional<Integer> nOpt,
             @RequestParam("sortBy") Optional<String> sortOpt,
             @RequestParam("orgId") Optional<String> orgId,
             @RequestParam("sortDir") Optional<Boolean> dirOpt){

        String property =  sortOpt.orElse("updateAt");
        Boolean direction = dirOpt.orElse(true);
        Sort sort = Sort.by(direction? Sort.Direction.DESC : Sort.Direction.ASC ,property);

        Pageable pageable =  PageRequest.of(pageOpt.orElse(0),nOpt.orElse(15),sort);
        Page<PlantTypeDto> page = plantTypeService.findByPagination(orgId.orElse(""),pageable);

        return new ResponseEntity<>(page,HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> createPlantType(@RequestBody PlantTypeDto plantTypeDto){
            plantTypeService.create(plantTypeDto);
        return new ResponseEntity<>(plantTypeDto,HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<?> getByIdPlantType(@PathVariable("id") ObjectId id){
        return new ResponseEntity<>(plantTypeService.getById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> updateById(@PathVariable("id") ObjectId id, @RequestBody PlantTypeDto plantTypeDto){
            plantTypeService.update(id, plantTypeDto);
            return new ResponseEntity<>(plantTypeDto,HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> deleteById(@PathVariable("id") ObjectId id){
        plantTypeService.deleteById(id);
        return new ResponseEntity<>("Delete success by id "+ id,HttpStatus.OK);
    }
}
