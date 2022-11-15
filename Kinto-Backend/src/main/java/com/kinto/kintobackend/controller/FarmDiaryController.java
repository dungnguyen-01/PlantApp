package com.kinto.kintobackend.controller;

import com.kinto.kintobackend.dto.FarmDiaryDto;
import com.kinto.kintobackend.service.FarmDiaryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"https://app.kintovn.com","http://localhost:8080"})
@RequestMapping("/api/v1/farm-diary")
public class FarmDiaryController {
    @Autowired
    FarmDiaryService farmDiaryService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> create(@RequestBody FarmDiaryDto farmDiaryDto){
        farmDiaryService.create(farmDiaryDto);
        return new  ResponseEntity<>(farmDiaryDto,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") ObjectId id){
        return new ResponseEntity<>(farmDiaryService.getById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> update(@PathVariable("id") ObjectId id, @RequestBody FarmDiaryDto farmDiaryDto) {
        farmDiaryService.update(id, farmDiaryDto);
        System.out.println(farmDiaryDto);
        System.out.println(id);
        return new ResponseEntity<>(farmDiaryDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> delete(@PathVariable("id") ObjectId id) {
        farmDiaryService.deleteById(id);
        return new ResponseEntity<>("Delete success by id "+ id,HttpStatus.OK);
    }

    @DeleteMapping("/plant/{plantId}")
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> deleteByPlantId(@PathVariable("plantId") ObjectId id) {
        farmDiaryService.deleteByPlantId(id);
        return new ResponseEntity<>("Delete success by id "+ id,HttpStatus.OK);
    }
}
