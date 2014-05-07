package com.kendelong.functional.user

import com.kendelong.functional.BaseWebService


class LoginService_4 extends BaseWebService
{
	private String serviceUri = '/public/v1/login'
	
	def login(String email, String password, def success, def failure)
	{
		def body = [email: email, password: password]
		callService(serviceUri, body, null, success, failure)
	}
}
