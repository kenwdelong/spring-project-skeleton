package com.kendelong.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.FilterRegistration.Dynamic;

import org.springframework.core.Conventions;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.kendelong.web.WebassetHandlingFilter;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		return new Class<?>[] { AppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings()
	{
		return new String[] { "/" };
	}
	
//	@Override
//	protected Filter[] getServletFilters()
//	{
//		return new Filter[] {new DelegatingFilterProxy("springSecurityFilterChain")};
//	}
	
	// Do it this way instead, otherwise the filter is only mapped to the Dispatcher servlet
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException
	{
		super.onStartup(servletContext);

		Filter filter = new DelegatingFilterProxy("springSecurityFilterChain");
		String filterName = Conventions.getVariableName(filter);
		Dynamic registration = servletContext.addFilter(filterName, filter);
		registration.setAsyncSupported(isAsyncSupported());
		registration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
		
		filter = new WebassetHandlingFilter();
		filterName = "webassetHandlingFilter";
		registration = servletContext.addFilter(filterName, filter);
		registration.setAsyncSupported(isAsyncSupported());
		registration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/webassets/*");

	}

}
