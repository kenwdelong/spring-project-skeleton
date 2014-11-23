package com.kendelong.web;

import javax.annotation.PostConstruct;
import static groovyx.gpars.actor.Actors.*

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SlowClient slowClient;

	def slowActor
	
	@PostConstruct
	def createActor()
	{
		slowActor = actor {
			loop {
				react {
					slowClient.callSlowWebService()
					logger.info("Slow web service returned")
				}
			}
		}
	}

	@RequestMapping("/public/callslowAsync")
	public WebServiceResponse callSlowlyAndAsynchronously() throws Exception
	{
		logger.info("Entering callSlowlyAndAsynchronously()");
		slowActor << "Go"
		WebServiceResponse webServiceResponse = new WebServiceResponse(status: 'success', message: 'Sent async call')
		logger.info("Leaving callSlowlyAndAsynchronously()");
		return webServiceResponse;
	}

}
