package com.equity.equitytest.service.iservice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.equity.equitytest.pojo.request.EmployeeRequest;

@Service
public interface EmployeeService {
    public ResponseEntity<String> viewList(String guid, String prefix, EmployeeRequest request);
}
