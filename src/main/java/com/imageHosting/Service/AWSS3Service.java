package com.imageHosting.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.imageHosting.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
public class AWSS3Service implements FileService {

    public String bucketName = "shuaochen";

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Override
    public String uploadFile(MultipartFile multipartFile){
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        String key = UUID.randomUUID().toString() + "." + extension;

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try{
            amazonS3Client.putObject(bucketName, key, multipartFile.getInputStream(), objectMetadata)
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload file");
        }

        amazonS3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

        return amazonS3Client.getResourceUrl(bucketName, key);
    }
}
