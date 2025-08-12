package com.equity.equitytest.helper.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.equity.equitytest.pojo.request.LogRequest;

@Component
public class HitLogHelper {

    @Autowired
    private RestTemplate restTemplate;

    @Value("#{${api.url.log}}")
    private String baseUrl;


    public HttpStatus hitLog(LogRequest logRequest) {
        String url = baseUrl + "/api_fe/postlog";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<LogRequest> request = new HttpEntity<>(logRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.POST, request, String.class);

        return response.getStatusCode();
    }
}
