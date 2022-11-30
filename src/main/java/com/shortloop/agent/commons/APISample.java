package com.shortloop.agent.commons;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class APISample {
    private String rawUri;
    private String applicationName;
    private String hostName;
    private Map<String, String[]> parameters;
    private Map<String, String> requestHeaders;
    private Map<String, String> responseHeaders;
    private int statusCode;
    private String requestPayload;
    private String responsePayload;
    private String uncaughtExceptionMessage;
    private Boolean requestPayloadOmitted;
    private Boolean responsePayloadOmitted;
    private Long latency;
}
