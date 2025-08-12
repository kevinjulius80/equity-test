package com.equity.equitytest.helper.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.equity.equitytest.model.employee.Employee;
import com.equity.equitytest.pojo.response.EmployeeResponsePojo;
import com.google.gson.Gson;

@Component
public class ResponseBuilder {

    @Autowired
    Gson gson;
    
    public ResponseEntity<String> buildResponseList(HttpStatus status, List<Employee> employees) {
        List<EmployeeResponsePojo> employeeResponseList = new ArrayList<>();
        for (Employee employee : employees) {
            StringBuilder pathHierarchy = new StringBuilder(employee.getEmployeeName());
            StringBuilder employeeFormat = new StringBuilder(employee.getEmployeeName());
            int pathLevel = 0;
            Integer managerId = employee.getEmployeeManagerId();
            String managerName = "";
            while (managerId != null) {
                final Integer currentManagerId = managerId;
                Employee manager = employees.stream().filter(e -> e.getEmployeeId().equals(currentManagerId)).findFirst().get();
                pathHierarchy.insert(0, manager.getEmployeeName().concat("->"));
                employeeFormat.insert(0, "|__");
                pathLevel++;
                managerId = manager.getEmployeeManagerId();
                managerName = employee.getEmployeeManagerId() == null ? "" : employees.stream().filter(e -> e.getEmployeeId().equals(employee.getEmployeeManagerId())).findFirst().get().getEmployeeName();
            }

            EmployeeResponsePojo employeeResponse = EmployeeResponsePojo.builder()
                .employeeId(employee.getEmployeeId())
                .employeeName(employee.getEmployeeName())
                .managerName(managerName)
                .path_level(String.valueOf(pathLevel))
                .employeeFormat(employeeFormat.toString())
                .pathHeirarchy(pathHierarchy.toString())
                .build();
            employeeResponseList.add(employeeResponse);
        }

        return ResponseEntity.status(status).body(gson.toJson(employeeResponseList));
    }

}
