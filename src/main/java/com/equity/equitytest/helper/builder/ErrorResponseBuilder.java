package com.equity.equitytest.helper.builder;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.equity.equitytest.pojo.response.ErrorResponsePojo;
import com.google.gson.Gson;

@Component
public class ErrorResponseBuilder {
    @Autowired
    Gson gson;
    
    public ResponseEntity<String> build(HttpStatus status, String title, String detail) {
        ErrorResponsePojo.Error error = ErrorResponsePojo.Error.builder()
            .code(String.valueOf(status.value()))
            .title(title)
            .details(detail)
            .build();
        ErrorResponsePojo errorResponse = new ErrorResponsePojo(Arrays.asList(error));

        return ResponseEntity.status(status).body(gson.toJson(errorResponse));
    }
}
