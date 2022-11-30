package com.shortloop.agent;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shortloop.agent.commons.APISample;
import com.shortloop.agent.commons.BufferEntry;
import com.shortloop.agent.commons.SimpleSuccessResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApiProcessor {
    private static final Log logger = LogFactory.getLog(ApiProcessor.class);
    private final HttpClient httpClient;


    public void processRegisteredApi(ShortloopSpringFilter.RequestResponseContext context, final FilterChain filterChain) throws ServletException, IOException, HttpException {
        doFilter(filterChain, context);
        tryOffering(context);
    }

    private void tryOffering(ShortloopSpringFilter.RequestResponseContext context) throws HttpException {
        BufferEntry bufferEntry = getBufferEntryForApiSample(context);
        HttpRequest<String, SimpleSuccessResponse> httpRequestDetail =
                new HttpRequest("send-sample", Lists.newArrayList(bufferEntry.getContent()), null, null, new TypeReference<SimpleSuccessResponse>() {});
        this.httpClient.postRequest(httpRequestDetail);
    }

    private void doFilter(final FilterChain filterChain, ShortloopSpringFilter.RequestResponseContext context) throws ServletException, IOException {
        filterChain.doFilter(context.getServletRequest(), context.getServletResponse());
    }
    private BufferEntry getBufferEntryForApiSample(ShortloopSpringFilter.RequestResponseContext context) {
        APISample apiSample = new APISample();
        try {
            apiSample.setParameters(getParameters(context.getServletRequest()));
            apiSample.setRequestHeaders(getRequestHeaders(context.getServletRequest()));
            apiSample.setResponseHeaders(getResponseHeaders(context.getServletResponse()));
            apiSample.setHostName(context.getServletRequest().getServerName());
            apiSample.setStatusCode(context.getServletResponse().getStatus());
        } catch (Exception e) {
        }
        return () -> apiSample;
    }


    private Map<String, String> getRequestHeaders(@NonNull final HttpServletRequest servletRequest) {
        Enumeration<String> headerNames = servletRequest.getHeaderNames();
        Map<String, String> headerMap = Maps.newHashMap();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headerMap.put(headerName, servletRequest.getHeader(headerName));
            }
        }
        return headerMap;
    }

    private Map<String, String> getResponseHeaders(@NonNull final HttpServletResponse servletResponse) {
        Map<String, String> headerMap = Maps.newHashMap();
        headerMap.put("Content-Type", servletResponse.getContentType());
        return headerMap;
    }

    private Map<String, String[]> getParameters(HttpServletRequest servletRequest) {
        Map<String, String[]> servletParameterMap = servletRequest.getParameterMap();
        Map<String, String[]> parameterMap = Maps.newHashMap();
        for (String key : servletParameterMap.keySet()) {
            parameterMap.put(key, servletParameterMap.get(key));
        }
        return parameterMap;
    }
}
