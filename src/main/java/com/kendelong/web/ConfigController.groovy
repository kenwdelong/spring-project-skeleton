package com.kendelong.web;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController
{
	@Value('${build.number}')
	private String buildNumber

	@Value('${build.time}')
	private String buildTime

	@RequestMapping("/public/config")
	public WebServiceResponse showConfig()
	{
		println TimeZone.getDefault().getID()
		
		
		def data = [buildNumber: buildNumber, buildTime: buildTime]
		return new WebServiceResponse(status: 'success', message: 'successfully discovered config', payload: data);
	}
}
