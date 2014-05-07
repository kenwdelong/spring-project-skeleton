package com.kendelong.service.security;

import groovy.json.JsonBuilder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class AuthenticationDeniedEntryPoint implements AuthenticationEntryPoint
{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException
	{
		JsonBuilder builder = new JsonBuilder()
		builder {
			status 'failure'
			message 'not authenticated' 
		}
		String json = builder.toString()
		response.setStatus(HttpServletResponse.SC_FORBIDDEN)
		response.setContentType('application/json')
		response.getOutputStream().withWriter { it.print(json) }
	}

}
