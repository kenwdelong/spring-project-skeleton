package com.kendelong.functional

import groovyx.net.http.HTTPBuilder;
import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.*
import groovyx.net.http.HTTPBuilder

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

import org.apache.commons.lang.StringUtils
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.conn.ssl.X509HostnameVerifier

/**
 * For info on HTTPBuilder, see http://groovy.codehaus.org/modules/http-builder/home.html
 * @author Ken
 *
 */
class BaseWebService
{
	protected HTTPBuilder http
	protected def standardFailed = {resp -> println resp.entity.writeTo(System.out); throw new RuntimeException(resp.statusLine.statusCode.toString())}

	BaseWebService()
	{	
		def nullTrustManager = [
			checkClientTrusted: { chain, authType ->  },
			checkServerTrusted: { chain, authType ->  },
			getAcceptedIssuers: { null }
		]
		
		def nullHostnameVerifier = [
			verify: { hostname, session -> true }
		]
		
		SSLContext sc = SSLContext.getInstance("SSL")
		sc.init(null, [nullTrustManager as X509TrustManager] as TrustManager[], null)
		
		def url = findBaseUrl()
		int port = url.split(':')[2].toInteger()

		SSLSocketFactory sf = new SSLSocketFactory(sc, nullHostnameVerifier as X509HostnameVerifier)
		Scheme scheme = new Scheme('https', port, sf)
		
		http = new HTTPBuilder(url)
		
		http.client.connectionManager.schemeRegistry.register(scheme)
	}
	
	private String findBaseUrl()
	{
		String prop = System.getProperty("myspringapp.baseUrl");
		if(StringUtils.isNotBlank(prop))
			return prop;
		else
			return "http://localhost:8080";
	}
	
	protected def callService(String serviceUri, def theBody, String token, def successHandler, def failureHandler)
	{
		http.request(POST, JSON) {
			req ->
			uri.path = serviceUri
			if(token) headers.'X-MySpringApp-Auth' = token
			if(theBody) body = theBody
			response.success = successHandler
			response.failure = failureHandler
		}
	}

	protected def callGetService(String serviceUri, String token, def successHandler, def failureHandler)
	{
		http.request(GET, JSON) {
			req ->
			uri.path = serviceUri
			if(token) headers.'X-MySpringApp-Auth' = token
			response.success = successHandler
			response.failure = failureHandler
		}
	}

}
