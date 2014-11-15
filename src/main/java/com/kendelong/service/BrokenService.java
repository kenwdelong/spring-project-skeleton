package com.kendelong.service;

import org.springframework.stereotype.Service;

import com.kendelong.util.circuitbreaker.CircuitBreakable;

@Service
public class BrokenService
{
	@CircuitBreakable
	public String callMe()
	{
		throw new RuntimeException("I'm broken");
	}
	
	@CircuitBreakable
	public String callAgain()
	{
		return "OK";
	}
}
