package com.fileuploadandserve.services.implementations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fileuploadandserve.services.FileService;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile file) {

        // file name
        String name = file.getOriginalFilename();

        // craete radom name of the image
        String randomId = UUID.randomUUID().toString();
        String randomNamePath = randomId.concat(name.substring(name.lastIndexOf(".")));

        // full path
        // String filePath = path + File.separator + name;
        String filePath = path + File.separator + randomNamePath;

        // create folder if not created
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        // file copy
        try {
            Files.copy(file.getInputStream(), Paths.get(filePath));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return name;
    }
    
}
