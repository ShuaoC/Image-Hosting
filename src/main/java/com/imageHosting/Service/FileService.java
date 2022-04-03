package com.imageHosting.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    String uploadFile(MultipartFile multipartFile);
    byte[] downloadFile(String fileName);
    List<String> listFiles();
}
