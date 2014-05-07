package com.kendelong.service.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

public class HeaderBasedTokenRememberMeServices extends TokenBasedRememberMeServices
{
	private String headerName; 
	
	@SuppressWarnings("deprecation")
	public HeaderBasedTokenRememberMeServices()
	{
		super();
	}
	
	public HeaderBasedTokenRememberMeServices(String key, UserDetailsService userDetailsService)
	{
		super(key, userDetailsService);
	}
	
	@Override
	protected String extractRememberMeCookie(HttpServletRequest request)
	{
		String header = request.getHeader(headerName);
		return header;
	}
	
	@Override
	protected void cancelCookie(HttpServletRequest request, HttpServletResponse response)
	{
		// No-op
	}
	
	@Override
	protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request,
			HttpServletResponse response)
	{
		// No-op
	}
	
	public String encodeToken(String username, String password)
	{
        int tokenLifetime = getTokenValiditySeconds();
        long expiryTime = System.currentTimeMillis();
        expiryTime += 1000L* (tokenLifetime < 0 ? TWO_WEEKS_S : tokenLifetime);
        String signatureValue = makeTokenSignature(expiryTime, username, password);

        String[] tokens = new String[] {username, Long.toString(expiryTime), signatureValue};
		String value = encodeCookie(tokens);
		return value;
	}

	public String getHeaderName()
	{
		return headerName;
	}

	public void setHeaderName(String headerName)
	{
		this.headerName = headerName;
	}
}
