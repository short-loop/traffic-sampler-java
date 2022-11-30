package com.shortloop.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;


@AllArgsConstructor
@Slf4j
@Component
public class HttpClient {

    HttpClientConfig httpClientConfig;
    private RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public HttpClient(HttpClientConfig httpClientConfig) {
        this.httpClientConfig = httpClientConfig;
        this.objectMapper = new ObjectMapper();
        this.restTemplate = new RestTemplateBuilder().
                build();
    }

    public <T, V> ResponseEntity<V> postRequest(HttpRequest<T, V> requestDetail) throws HttpException {
        return request(requestDetail, HttpMethod.POST);
    }

    private <T, V> ResponseEntity<V> request(HttpRequest<T, V> requestInfo, HttpMethod method) throws HttpException {
        try {
            ResponseEntity<String> response = restTemplate.exchange(requestInfo.getUrl(), method, new HttpEntity<>(requestInfo.getRequestBody(), null), String.class);
            V res = ObjectUtils.isEmpty(response.getBody()) ? null : objectMapper.readValue(response.getBody(), requestInfo.getResponseType());
            return new ResponseEntity<>(res, response.getHeaders(), response.getStatusCode());
        } catch (Exception e) {
            throw new HttpException(e.getMessage());
        }
    }
}

