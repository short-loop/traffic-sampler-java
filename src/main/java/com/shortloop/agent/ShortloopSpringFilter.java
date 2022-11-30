package com.shortloop.agent;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order
@Service
public class ShortloopSpringFilter {
    private ApiProcessor apiProcessor;

    @Getter
    @Setter
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class RequestResponseContext {
        final HttpServletRequest servletRequest;
        final HttpServletResponse servletResponse;
    }
    protected void filter(
            final HttpServletRequest servletRequest,
            final HttpServletResponse servletResponse,
            final FilterChain filterChain)
            throws ServletException, IOException, HttpException {
        RequestResponseContext context = new RequestResponseContext(servletRequest, servletResponse);
        apiProcessor.processRegisteredApi(context, filterChain);
    }
}
