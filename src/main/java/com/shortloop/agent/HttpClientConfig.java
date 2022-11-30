package com.shortloop.agent;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class HttpClientConfig {
    @Value("#{new Integer('${maxTotalConnections:10}')}")
    private Integer maxConnTotal;
}
