package com.equity.equitytest.service.iservice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploadFileService {
    public ResponseEntity<String> uploadFile(String guid, String title, MultipartFile file);
}
