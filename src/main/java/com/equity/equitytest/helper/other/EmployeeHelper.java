package com.equity.equitytest.helper.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.equity.equitytest.model.employee.Employee;

@Component
public class EmployeeHelper {
    public int getTotalSubordinates(String employeeName, Map<Integer, Employee> employeeMap) {
        int total = 0;
        for (Employee e : employeeMap.values()) {
            if (e.getEmployeeName() != null && e.getEmployeeName() == employeeName) {
                total += 1 + getTotalSubordinates(e.getEmployeeName(), employeeMap); // untuk menghitung jumlah bawahan
            }
        }
        return total;
    }

    public static int countSubordinates(Map<Integer, Employee> employeeMap, int managerId) {
        int count = 0;
        for (Employee employee : employeeMap.values()) {
            if (employee != null && employee.getEmployeeManagerId() != null && managerId == employee.getEmployeeManagerId()) {
                // Increment count for each direct subordinate
                count++;
                // Count the subordinates of this subordinate
                count += countSubordinates(employeeMap, employee.getEmployeeId());
            }
        }
        return count;
    }

    public List<Integer> getAllSuperiors(int employeeId, Map<Integer, Employee> employeeMap) {
        List<Integer> superiors = new ArrayList<>();

        // Ambil employee berdasarkan employeeId dari map
        Employee employee = employeeMap.get(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
        }

        // Lakukan iterasi untuk mencari semua atasan
        Integer managerId = employee.getEmployeeManagerId();
        while (managerId != null) {
            Employee manager = employeeMap.get(managerId);
            if (manager != null) {
                superiors.add(manager.getEmployeeId());
                managerId = manager.getEmployeeManagerId(); // Update ke manajer berikutnya
            } else {
                break; // Berhenti jika manajer tidak ditemukan
            }
        }

        return superiors;
    }
}
