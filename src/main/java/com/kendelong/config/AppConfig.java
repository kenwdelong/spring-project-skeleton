package com.kendelong.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:/META-INF/spring/application-context.xml", "classpath:/META-INF/spring/security.xml"})
@ComponentScan(basePackages="com.kendelong.service,com.kendelong.util.jmx.web.service")
public class AppConfig
{

}
