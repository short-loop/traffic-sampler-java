package com.shortloop.agent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "shortloop.enabled", havingValue = "true")
public class ShortloopAutoConfiguration {

    private static final Log logger = LogFactory.getLog(ShortloopAutoConfiguration.class);

    @Value("${shortloop.ctUrl:}")
    private String ctUrl;

    @Value("${shortloop.applicationName:}")
    private String userApplicationName;

}
