package com.kendelong.functional.user

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.*

import com.kendelong.functional.BaseWebService

class UpdateUserService_3 extends BaseWebService
{
	private String serviceUri = '/user/v1/update'	
	
	def updateUserEmail(String email, String token, def success, def failure)
	{
		def body = [email: email]
		callService(serviceUri, body, token, success, failure)
	}
	
	def updateUserPassword(String password, String token, def success, def failure)
	{
		def body = [password: password]
		callService(serviceUri, body, token, success, failure)
	}
	
	def updateBirthDate(String birthDate, String token, def success, def failure)
	{
		def body = [birthDate: birthDate]
		callService(serviceUri, body, token, success, failure)
	}
	
}
