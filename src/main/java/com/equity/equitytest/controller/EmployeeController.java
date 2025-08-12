package com.equity.equitytest.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.equity.equitytest.pojo.request.EmployeeRequest;
import com.equity.equitytest.service.iservice.EmployeeService;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = {"/api_fe/list_employee"})
    public ResponseEntity<String> viewListRequest(@RequestBody EmployeeRequest request) {
        String guid = UUID.randomUUID().toString();

        return employeeService.viewList(guid, "[LIST EMPLOYEE]", request);
    }

    
}
