package com.equity.equitytest.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EmployeeRequest {
    private RequestQueryPOJO query;

    @Data
    public static class RequestQueryPOJO {
        private String order;
        private Integer page;
        private SearchQueryPOJO search;

        @JsonProperty("page-size")
        private Integer pageSize;

        @JsonProperty("order-by")
        private Integer orderBy;
    }

    @Data
    public static class SearchQueryPOJO {

        @JsonProperty("employee-name")
        private String employeeName;
    }
}
