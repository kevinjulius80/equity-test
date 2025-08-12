package com.equity.equitytest.pojo.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponsePojo {
    private List<Error> errors;
    
    @Data
    @Builder
    @AllArgsConstructor
    public static class Error {
        private String code;
        private String title;
        private String details;
    }
}
