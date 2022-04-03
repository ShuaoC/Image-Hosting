package com.imageHosting.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClientService implements FileService {

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
            amazonS3Client.putObject(bucketName, key, multipartFile.getInputStream(), objectMetadata);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload file");
        }

        amazonS3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

        return amazonS3Client.getResourceUrl(bucketName, key);
    }

    @Override
    public byte[] downloadFile(String filename){
        S3Object s3Object = amazonS3Client.getObject(bucketName, filename);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] file = IOUtils.toByteArray(inputStream);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> listFiles() {

        com.amazonaws.services.s3.model.ListObjectsRequest listObjectsRequest =
                new com.amazonaws.services.s3.model.ListObjectsRequest()
                        .withBucketName(bucketName);

        List<String> keys = new ArrayList<>();

        ObjectListing objects = amazonS3Client.listObjects(listObjectsRequest);

        while (true) {
            List<S3ObjectSummary> objectSummaries = objects.getObjectSummaries();
            if (objectSummaries.size() < 1) {
                break;
            }

            for (S3ObjectSummary item : objectSummaries) {
                if (!item.getKey().endsWith("/"))
                    keys.add(item.getKey());
            }

            objects = amazonS3Client.listNextBatchOfObjects(objects);
        }

        return keys;
    }
}
