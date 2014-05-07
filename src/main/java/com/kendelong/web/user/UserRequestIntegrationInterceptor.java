package com.kendelong.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kendelong.domain.User;
import com.kendelong.service.dao.UserRepository;
import com.kendelong.service.security.IUserHolder;

@Component
public class UserRequestIntegrationInterceptor extends HandlerInterceptorAdapter
{
	
	@Autowired
	private IUserHolder userHolder;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception
	{
		String id = request.getRemoteUser();
		if(NumberUtils.isNumber(id))
		{
			Long userId = Long.parseLong(id);
			User user = userRepo.findOne(userId);
			userHolder.setUser(user);
		}
		return true;
	}
}
