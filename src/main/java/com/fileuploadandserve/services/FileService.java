package com.fileuploadandserve.services;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadImage(String path, MultipartFile image);
    InputStream getResource(String path, String fileName) throws FileNotFoundException;
}
