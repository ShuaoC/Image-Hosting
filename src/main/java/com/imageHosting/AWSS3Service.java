package com.imageHosting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.util.UUID;

@Service
public class AWSS3Service implements FileService{

    @Autowired
    public String uploadFile(MultipartFile multipartFile){
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        String key = UUID.randomUUID().toString() + "." + extension;

        ObjectMetadata metaData = new ObjectMetaData();
        metaData
    }
}
