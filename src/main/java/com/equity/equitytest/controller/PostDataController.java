package com.equity.equitytest.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.equity.equitytest.pojo.request.EmployeeRequest;
import com.equity.equitytest.service.iservice.EmployeeService;
import com.equity.equitytest.service.iservice.UploadFileService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class PostDataController {

    @Autowired
    private UploadFileService uploadfileService;

    @PostMapping(value = {"/api_fe/postdata"})
    public ResponseEntity<String> viewListRequest(@RequestParam("file") MultipartFile file) {
        String guid = UUID.randomUUID().toString();

        return uploadfileService.uploadFile(guid, "[Upload Data]", file);
    }

}
