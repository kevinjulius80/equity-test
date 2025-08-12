package com.equity.equitytest.repository.dbemployee;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.equity.equitytest.model.employee.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    List<Employee> findAllByEmployeeId(Integer employeeId);
    
}
