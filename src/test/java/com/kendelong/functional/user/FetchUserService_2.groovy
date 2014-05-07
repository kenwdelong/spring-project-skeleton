package com.kendelong.functional.user

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.*

import com.kendelong.functional.BaseWebService

class FetchUserService_2 extends BaseWebService
{
	def fetchUser(Long id, String token, def success, def failure)
	{
		callService(getServiceUri(id), null, token, success, failure)
	}

	private String getServiceUri(Long id)
	{
		return "/user/v1/${id}"
	}
	

}
