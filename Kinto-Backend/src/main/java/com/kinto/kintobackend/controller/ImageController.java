package com.kinto.kintobackend.controller;

import com.kinto.kintobackend.dto.ImageDto;
import com.kinto.kintobackend.model.Image;
import com.kinto.kintobackend.service.ImageService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = {"https://app.kintovn.com","http://localhost:8080"})
@RequestMapping("/api/v1/image")
public class ImageController {

    @Autowired
    ImageService imageService;
    @Autowired
    HttpServletRequest request;

    @GetMapping
    public ResponseEntity<?> getAllImage(){
        List<ImageDto> imagesDtos = imageService.findAllImage();
        return new ResponseEntity<>(imagesDtos,HttpStatus.OK);
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> create(@RequestParam("orgId") String orgId,
                                    @RequestParam("plantId") ObjectId plantId ,
                                    @RequestPart("files")List<MultipartFile> files) throws IOException {
        imageService.create(plantId, orgId,files);
        return new ResponseEntity<>("ok nha", HttpStatus.OK);

    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> getByIdImage(@PathVariable(value = "id") ObjectId id){
        if (StringUtils.isEmpty(id)) {
            return new ResponseEntity<>("id not empty"+id,HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(imageService.getUrl(id), HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> updateStatus(@PathVariable("id") ObjectId id){
        imageService.updateStatus(id);
        return new ResponseEntity<>("Update success id "+ id, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> deleteById(@PathVariable("id") ObjectId id) {
        System.out.println("chay vao day "+ id);
        imageService.deleteById(id);
        return new ResponseEntity<>("Delete success by id "+ id,HttpStatus.OK);
    }
    @DeleteMapping("/plant/{plantId}")
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Editor')")
    public ResponseEntity<?> deleteByPlantId(@PathVariable("plantId") ObjectId id) {
        System.out.println("chay vao day "+ id);
        imageService.deleteByPlantId(id);
        return new ResponseEntity<>("Delete success by id "+ id,HttpStatus.OK);
    }
}
