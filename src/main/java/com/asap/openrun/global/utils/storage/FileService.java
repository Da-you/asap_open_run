package com.asap.openrun.global.utils.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String upload(MultipartFile file ,String destLocation);
void delete(String destLocation, String destKey);
}
