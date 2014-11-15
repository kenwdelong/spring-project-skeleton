package com.kendelong.service;

import org.springframework.stereotype.Service;

import com.kendelong.util.concurrency.ConcurrencyThrottle;

@Service
public class ServiceB
{
	@ConcurrencyThrottle
	public String callMe()
	{
		return "This is service B calling";
	}
}
