package com.kendelong.service;

import org.springframework.stereotype.Service;

import com.kendelong.util.performance.MonitorPerformance;
import com.kendelong.util.retry.RetryableOperation;

@Service
@MonitorPerformance
public class ServiceA
{
	private int count = 0;
	
	@RetryableOperation
	public String callMe()
	{
		if(++count%3 == 0)
			throw new IllegalArgumentException("Bad luck, it broke");
		
		return "This is service A calling";
	}
}
