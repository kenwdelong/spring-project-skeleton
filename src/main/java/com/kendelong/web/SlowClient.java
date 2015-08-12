package com.kendelong.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kendelong.util.http.HttpResponseObject;
import com.kendelong.util.http.IHttpConnectionService;
import com.kendelong.util.monitoring.webservice.WebServiceClient;
import com.kendelong.util.performance.MonitorPerformance;

@WebServiceClient
@MonitorPerformance  // needed to activate the pointcut
@Component
public class SlowClient
{
	@Autowired
	private IHttpConnectionService httpConnectionService;
	
	public WebServiceResponse callSlowWebService() throws Exception
	{
		HttpResponseObject response = httpConnectionService.simpleGet("http://localhost:8080/public/slow");
		WebServiceResponse webServiceResponse = new WebServiceResponse();
		webServiceResponse.setMessage(response.getStatusText());
		webServiceResponse.setStatus("success");
		return webServiceResponse;
	}
}
