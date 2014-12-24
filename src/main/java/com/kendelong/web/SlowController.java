package com.kendelong.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kendelong.util.monitoring.webservice.WebServiceEndpoint;

@RestController
@WebServiceEndpoint
public class SlowController
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/public/slow")
	public WebServiceResponse respondSlowly() throws Exception
	{
		logger.info("Entering respondSlowly()");
		Thread.sleep(10*1000);
		WebServiceResponse webServiceResponse = new WebServiceResponse();
		webServiceResponse.setMessage("Success");
		webServiceResponse.setStatus("success");
		logger.info("Exiting respondSlowly()");
		return webServiceResponse;
	}


}
