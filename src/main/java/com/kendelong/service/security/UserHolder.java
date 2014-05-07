package com.kendelong.service.security;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.kendelong.domain.User;

@Component
@Scope(proxyMode=ScopedProxyMode.INTERFACES, value="request")
public class UserHolder implements IUserHolder
{
	private User user;
	
	@Override
	public boolean isAuthenticated()
	{
		return user != null;
	}

	@Override
	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
	
	
}
