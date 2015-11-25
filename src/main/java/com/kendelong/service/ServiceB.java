package com.kendelong.service;

import org.springframework.stereotype.Service;

import com.kendelong.util.concurrency.ConcurrencyThrottle;
import com.kendelong.util.performance.MonitorPerformance;

@Service
@MonitorPerformance
public class ServiceB
{
	@ConcurrencyThrottle(threadLimit=4)
	public String callMe()
	{
		return "This is service B calling";
	}
	
}
