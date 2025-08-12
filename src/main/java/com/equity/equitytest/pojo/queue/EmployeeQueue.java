package com.equity.equitytest.pojo.queue;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * @author kevin.saputra
 */
@Setter
@Getter
public class EmployeeQueue {
    @SerializedName("type")
    private String type;
    @SerializedName("employee_name")
    private String employeeName;
    @SerializedName("employee_manager_id")
    private Integer employeeManagerId;
}
