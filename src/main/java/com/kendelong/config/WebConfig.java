package com.kendelong.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.kendelong.web.user.UserHandlerMethodArgumentResolver;
import com.kendelong.web.user.UserRequestIntegrationInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.kendelong.web")
@ImportResource("classpath:/META-INF/spring/mvc.xml")
public class WebConfig extends WebMvcConfigurerAdapter
{
	
	@Autowired
	UserRequestIntegrationInterceptor userRequestIntegrationInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(userRequestIntegrationInterceptor);
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
	{
		argumentResolvers.add(userHandlerMethodArgumentResolver());
	}
	
	@Bean
	public UserHandlerMethodArgumentResolver userHandlerMethodArgumentResolver()
	{
		return new UserHandlerMethodArgumentResolver();
	}
}
