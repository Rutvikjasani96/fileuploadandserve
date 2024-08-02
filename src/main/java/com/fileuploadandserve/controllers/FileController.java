package com.fileuploadandserve.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fileuploadandserve.payloads.FileResponse;
import com.fileuploadandserve.services.FileService;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;
    
    @PostMapping("/upload")
    public ResponseEntity<FileResponse> fileUpload(
        @RequestParam("image") MultipartFile image
    ){
        String fileName = null;
        try {
            fileName = fileService.uploadImage(path, image);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<FileResponse>(new FileResponse(fileName,"File was not Uploaded..."),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<FileResponse>(new FileResponse(fileName,"File Uploaded Successfully..."),HttpStatus.OK);
    }
}
