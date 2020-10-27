package com.lyl.web.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {

    public String uploadTinyImage(String resourceReelParent, String fileParent, MultipartFile multipartFile);
}
