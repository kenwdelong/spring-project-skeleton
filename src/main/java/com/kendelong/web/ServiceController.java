package com.kendelong.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kendelong.service.BrokenService;
import com.kendelong.service.ServiceA;
import com.kendelong.service.ServiceB;

@Controller
public class ServiceController
{
	@Autowired
	private ServiceA serviceA;
	
	@Autowired
	private ServiceB serviceB;
	
	@Autowired
	private BrokenService brokenService;
	
	@RequestMapping("/public/serviceA")
	@ResponseBody
	public String invokeServiceA()
	{
		return serviceA.callMe();
	}
	
	@RequestMapping("/public/serviceB")
	@ResponseBody
	public String invokeServiceB()
	{
		return serviceB.callMe();
	}

	@RequestMapping("/public/brokenService1")
	@ResponseBody
	public String invokeBrokenService1()
	{
		return brokenService.callMe();
	}

	@RequestMapping("/public/brokenService2")
	@ResponseBody
	public String invokeBrokenService2()
	{
		return brokenService.callAgain();
	}

}
