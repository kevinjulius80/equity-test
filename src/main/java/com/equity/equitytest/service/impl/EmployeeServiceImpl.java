package com.equity.equitytest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.equity.equitytest.helper.builder.ErrorResponseBuilder;
import com.equity.equitytest.helper.builder.ResponseBuilder;
import com.equity.equitytest.model.employee.Employee;
import com.equity.equitytest.pojo.request.EmployeeRequest;
import com.equity.equitytest.repository.dbemployee.EmployeeRepository;
import com.equity.equitytest.service.iservice.EmployeeService;
import com.equity.equitytest.util.AppLogUtil;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private ErrorResponseBuilder errorResponseBuilder;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<String> viewList(String guid, String prefix, EmployeeRequest request) {
        try {
            List<Employee> employees = new ArrayList<>();
            employeeRepository.findAll().forEach(employees::add);
            return responseBuilder.buildResponseList(HttpStatus.OK, employees);
        } catch (Exception e) {
            AppLogUtil.WriteErrorLog(guid, prefix, e.toString());
            return errorResponseBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e.toString());
        }
    }
    
}
