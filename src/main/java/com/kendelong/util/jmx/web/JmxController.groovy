package com.kendelong.util.jmx.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JmxController
{
	@RequestMapping("/admin/jmx")
	public String getMainPage()
	{
		String page = this.getClass().getClassLoader().getResourceAsStream("com/kendelong/util/jmx/index.html").text;
	}
	
	@RequestMapping("/admin/jmx/beans")
	public String getBeanList()
	{
		def performance = [
			[
				"name": "ServiceController",
				"min": 1,
				"max": 135,
				"avg": 22,
				"rpm": 17.4,
				"ex": 0,
				"numAccess": 214,
				"cumulative": 4708
			],
			[
				"name": "ConfigController",
				"min": 1,
				"max": 21,
				"avg": 3,
				"rpm": 94.3,
				"ex": 0,
				"numAccess": 1334,
				"cumulative": 28014
			]
		];
		
		def circuitBreakers = [
			[
				"name": "Nice Service",
				"failureCount": 0,
				"state": "Closed",
				"threshold": 3,
				"recovery": 300,
				"lastTripTime": "",
				"timeSinceLastTrip": -1,
				"timeToNextRetry": 0,
				"totalNumTrips": 0
			],
			[
				"name": "Broken Service",
				"failureCount": 3,
				"state": "Open",
				"threshold": 3,
				"recovery": 300,
				"lastTripTime": "Jun 18, 2015 11:06:34 AM PDT",
				"timeSinceLastTrip": 110,
				"timeToNextRetry": 190,
				"totalNumTrips": 4
			]
		];
		
		def concurrencyThrottles = [
			[
				"name": "Lonely Service",
				"threadLimit": 20,
				"threadCount": 0,
				"tripCount": 0
			],
			[
				"name": "Popular Service",
				"threadLimit": 20,
				"threadCount": 6,
				"tripCount": 2
			]
		];

		def retries = [
			[
				"name": "Good Service",
				"maxRetries": 3,
				"numAccesses": 305,
				"retriedOperations": 0,
				"failedOperations": 0,
				"failedMethods": []
			],
			[
				"name": "Bad Service",
				"maxRetries": 3,
				"numAccesses": 213,
				"retriedOperations": 13,
				"failedOperations": 3,
				"failedMethods": [
					["name": "methodA", "failures": 1],
					["name": "methodB", "failures": 3]
				]
			]
		];


		return [performance: performance, circuitBreakers: circuitBreakers, concurrencyThrottles: concurrencyThrottles, retries: retries]
	}

	@RequestMapping("/admin/jmx/bean/{name}")
	public String getBeanData(@PathVariable('name') String name)
	{
		def methodPerformance = [
			[
				"name": "methodNumberOne",
				"min": 2,
				"max": 223,
				"avg": 54,
				"rpm": 19.2,
				"ex": 0,
				"numAccess": 112,
				"cumulative": 3800
			],
			[
				"name": "methodNumberTwo",
				"min": 1,
				"max": 21,
				"avg": 3,
				"rpm": 94.3,
				"ex": 0,
				"numAccess": 1334,
				"cumulative": 28014
			]
		];
		return methodPerformance
	}
}
