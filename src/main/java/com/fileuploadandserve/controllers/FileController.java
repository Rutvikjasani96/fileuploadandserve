package com.fileuploadandserve.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fileuploadandserve.payloads.FileResponse;
import com.fileuploadandserve.services.FileService;

import jakarta.servlet.http.HttpServletResponse;

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


    @GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
        @PathVariable("imageName") String imageName,
        HttpServletResponse response
    )throws IOException{
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
