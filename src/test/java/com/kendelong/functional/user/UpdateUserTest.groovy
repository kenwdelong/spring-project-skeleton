package com.kendelong.functional.user

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.*
import static org.junit.Assert.*

import org.apache.commons.lang.RandomStringUtils
import org.junit.Before
import org.junit.Test

import com.kendelong.functional.BaseTestClass

class UpdateUserTest extends BaseTestClass
{
	private UpdateUserService_3 updateUserService = new UpdateUserService_3()
	private CreateUserService_1 createUserService = new CreateUserService_1()	
	
	def data
	
	@Before
	public void setup()
	{
		data = createUserService.createUser()
	}
	
	@Test
	public void testUpdateEmail()
	{
		String email = "updated${RandomStringUtils.randomAlphanumeric(8)}@update.com"
		def success = {
			resp, json ->
			assertEquals("bad response code", 200, resp.statusLine.statusCode)
			assertEquals("bad status", 'success', json.status)
			assertEquals("same id", data.user.id, json.payload.id)
			assertEquals("wrong email", email, json.payload.email)  
			prettyPrint(json)
		}
		updateUserService.updateUserEmail(email, data.token, success, standardFailure)
	}

	@Test
	public void testUpdatePassword()
	{
		def password = "xb478f"
		def success = {
				resp, json ->
				assertEquals("bad response code", 200, resp.statusLine.statusCode)
				assertEquals("bad status", 'success', json.status)
				assertEquals("same id", data.user.id, json.payload.id)
		}
		updateUserService.updateUserPassword(password, data.token, success, standardFailure)
	}

	@Test
	public void testUpdateBirthDate()
	{
		def date = '2013-06-22'
		def success = {
				resp, json ->
				assertEquals("bad response code", 200, resp.statusLine.statusCode)
				assertEquals("bad status", 'success', json.status)
				assertEquals("same id", data.user.id, json.payload.id)
				assertEquals("wrong email", date, json.payload.birthDate)
		}
		updateUserService.updateBirthDate(date, data.token, success, standardFailure)
	}

}
