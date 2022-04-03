package com.imageHosting.Controller;

import com.imageHosting.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/imageHosting")
public class ImageHostingController {

    @Autowired
    private FileService fileService;

    @PostMapping("/images")
    public ResponseEntity<Map<String,String>> uploadFile(@RequestParam("file")MultipartFile multipartFile){
        String url = fileService.uploadFile(multipartFile);

        Map<String, String> response = new HashMap<>();

        response.put("URL", url);

        return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
    }

    @PostMapping("/test")
    public String testing(){
        return "Testing";
    }

}
