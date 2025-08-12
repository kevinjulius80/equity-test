package com.equity.equitytest.pojo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponsePojo {

    @SerializedName("employee_id")
    @JsonProperty("employee_id")
    private Integer employeeId;

    @SerializedName("employee_name")
    @JsonProperty("employee_name")
    private String employeeName;

    @SerializedName("manager_name")
    @JsonProperty("manager_name")
    private String managerName;

    @SerializedName("path_level")
    @JsonProperty("path_level")
    private String path_level;

    @SerializedName("employee_format")
    @JsonProperty("employee_format")
    private String employeeFormat;

    @SerializedName("path_heirarchy")
    @JsonProperty("path_heirarchy")
    private String pathHeirarchy;
}
