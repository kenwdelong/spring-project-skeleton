package com.kendelong.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class WebassetHandlingFilter
 */
@WebFilter("/WebassetHandlingFilter")
public class WebassetHandlingFilter implements Filter
{

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		RequestDispatcher dispatcher = request.getServletContext().getNamedDispatcher("default");
		dispatcher.forward(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException
	{
	}

	public void destroy()
	{
	}

}
