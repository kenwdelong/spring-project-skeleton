package com.kendelong.service.security;

import com.kendelong.domain.User;

public interface IUserHolder
{

	public boolean isAuthenticated();

	public User getUser();

	public void setUser(User user);

}