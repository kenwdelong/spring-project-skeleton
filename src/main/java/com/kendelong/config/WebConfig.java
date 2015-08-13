package com.kendelong.config;

import static org.ajar.swaggermvcui.SwaggerSpringMvcUi.WEB_JAR_RESOURCE_LOCATION;
import static org.ajar.swaggermvcui.SwaggerSpringMvcUi.WEB_JAR_RESOURCE_PATTERNS;
import static org.ajar.swaggermvcui.SwaggerSpringMvcUi.WEB_JAR_VIEW_RESOLVER_PREFIX;
import static org.ajar.swaggermvcui.SwaggerSpringMvcUi.WEB_JAR_VIEW_RESOLVER_SUFFIX;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.kendelong.web.user.UserHandlerMethodArgumentResolver;
import com.kendelong.web.user.UserRequestIntegrationInterceptor;
import com.mangofactory.swagger.plugin.EnableSwagger;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.kendelong.web,com.kendelong.util.jmx.web.controller")
@ImportResource("classpath:/META-INF/spring/mvc.xml")
@EnableSwagger
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
	
	
	// =========================== SWAGGER ===============================
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler(WEB_JAR_RESOURCE_PATTERNS)
				.addResourceLocations(WEB_JAR_RESOURCE_LOCATION).setCachePeriod(0);
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver()
	{
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix(WEB_JAR_VIEW_RESOLVER_PREFIX);
		resolver.setSuffix(WEB_JAR_VIEW_RESOLVER_SUFFIX);
		return resolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
	{
		configurer.enable();
	}

}
