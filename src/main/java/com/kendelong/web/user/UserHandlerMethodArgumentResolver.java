package com.kendelong.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.kendelong.domain.User;
import com.kendelong.service.security.IUserHolder;

public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver
{
	@Autowired
	private IUserHolder userHolder;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter)
	{
		if(parameter.getParameterType().equals(User.class)) 
			return true;
		else
			return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception
	{
		return userHolder.getUser();
	}

}
