package com.kendelong.service.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

public class HeaderDecodingRequestHeaderAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter 
{
	private String header;

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request)
	{
        String principal = request.getHeader(getHeader());

        if (principal == null) {
            throw new PreAuthenticatedCredentialsNotFoundException(header
                    + " header not found in request.");
        }

        return principal;
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request)
	{
		return null;
	}
	
	public String getHeader()
	{
		return header;
	}

	public void setHeader(String header)
	{
		this.header = header;
	}


}
