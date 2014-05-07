package com.kendelong.functional.user

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.*

import org.apache.commons.lang.RandomStringUtils

import com.kendelong.functional.BaseWebService

class CreateUserService_1 extends BaseWebService
{
	private String serviceUri = '/user/v1/create'	
	
	def createNewUser(def success, def failure)
	{
		String theemail = createRandomEmail()
		def body = [email: theemail, password: 'myPass', birthDate: '2014-02-09', name: 'Bubba', timezone: 'America/Los_Angeles']
		callService(serviceUri, body, null, success, failure)
	}
	
	def createUserWithoutEmail(def success, def failure)
	{
		def body = [password: 'myPass', birthDate: '2014-02-09', name: 'Bubba', timezone: 'America/Los_Angeles']
		callService(serviceUri, body, null, success, failure)
	}
	
	def createUserWithEmail(String email, def success, def failure)
	{
		def body = [email: email, password: 'myPass', birthDate: '2014-02-09', name: 'Bubba', timezone: 'America/Los_Angeles']
		callService(serviceUri, body, null, success, failure)
	}
	
	def createUserWithoutPassword(def success, def failure)
	{
		String theemail = createRandomEmail()
		def	body = [email: theemail, birthDate: '2014-02-09', name: 'Bubba', timezone: 'America/Los_Angeles']
		callService(serviceUri, body, null, success, failure)
	}
	
	def createUserWithoutBirthDate(def success, def failure)
	{
		def body = [email: createRandomEmail(), password: 'mypass', name: 'Bubba', timezone: 'America/Los_Angeles']
		callService(serviceUri, body, null, success, failure)
	}
	
	def createUserWithoutTimezone(def success, def failure)
	{
		String theemail = createRandomEmail()
		def body = [email: theemail, password: 'myPass', birthDate: '2014-02-09', name: 'Bubba']
		callService(serviceUri, body, null, success, failure)
	}
	
	def createNewUserWithBirthWeight(def birthWeight, def unit,  def success, def failure)
	{
		String theemail = createRandomEmail()
		def body = [birthWeight: birthWeight, unit: unit, email: theemail, password: 'myPass', birthDate: '2014-02-09', name: 'Bubba', timezone: 'America/Los_Angeles']
		callService(serviceUri, body, null, success, failure)
	}
	
	def createUser()
	{
		def token, user
		def body = [email: createRandomEmail(), password: 'myPass', birthDate: '2014-02-09', name: 'Bubba', timezone: 'America/Los_Angeles']
		def success = {
			resp, json ->
			token = json.token
			user = json.payload
		}
		callService(serviceUri, body, null, success, null)
		return [token: token, user: user, originalPassword: body.password]
	}
	
	private String createRandomEmail() 
	{
		String theemail = RandomStringUtils.randomAlphanumeric(8) + '@test.com'
		return theemail
	}

}
