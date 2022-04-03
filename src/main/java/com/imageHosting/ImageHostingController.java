package com.imageHosting;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@RestController
public class ImageHostingController {
    private final S3Client s3;
    String bucketname = "shuaochen";
    String key = "";

    ImageHostingController(S3Client s3) {
        this.s3 = s3;
    }

    @PostMapping("/images")
    public String uploadImage(){
        Region region = Region.US_EAST_1;
        s3 = S3Client.builder().region(region).build();
    }
}
