package com.kendelong.functional.user

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.*
import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import com.kendelong.functional.BaseTestClass

class CreateUserTest extends BaseTestClass
{
	private CreateUserService_1 service
		
	@Before
	public void setup()
	{
		service = new CreateUserService_1()
	}
		
	@Test
	public void testCreateUser()
	{
		def success = {
				resp, json ->
				assertEquals("bad response code", 200, resp.statusLine.statusCode)
				assertEquals("bad status", 'success', json.status)
				assertTrue("no id", json.payload.id as Boolean)
				assertTrue("no token", json.token as Boolean)
				prettyPrint(json)
		}
		service.createNewUser(success, standardFailure)
	}

	@Test
	public void testCreateUserWithoutEmailFails()
	{
		def failure = {
			resp, json ->
			assertEquals("bad response code", 400, resp.statusLine.statusCode)
			assertEquals("bad status", 'failure', json.status)
			assertEquals("wrong error code", '101', json.errorCode)
		}
		service.createUserWithoutEmail(failureBut200, failure)
	}
	
	@Test
	public void testCreateUserDuplicateEmailFails()
	{
		def data = service.createUser()
		def failure = {
			resp, json ->
			assertEquals("bad response code", 400, resp.statusLine.statusCode)
			assertEquals("bad status", 'failure', json.status)
			assertEquals("wrong error code", '102', json.errorCode)
		}
		service.createUserWithEmail(data.user.email, failureBut200, failure)
	}
	
	@Test
	public void testCreateUserWithoutPasswordFails()
	{
		def failure = {
			resp, json ->
			assertEquals("bad response code", 400, resp.statusLine.statusCode)
			assertEquals("bad status", 'failure', json.status)
			assertEquals("wrong error code", '105', json.errorCode)
		}
		service.createUserWithoutPassword(failureBut200, failure)
	}

	@Test
	public void testCreateUserWithoutBirthDateFails()
	{
		def failure = {
			resp, json ->
			assertEquals("bad response code", 400, resp.statusLine.statusCode)
			assertEquals("bad status", 'failure', json.status)
			assertEquals("wrong error code", '110', json.errorCode)
		}
		service.createUserWithoutBirthDate(failureBut200, failure)
	}
	
	@Test
	public void testCreateUserWithNoTimezone()
	{
		def failure = {
			resp, json ->
			assertEquals("bad response code", 400, resp.statusLine.statusCode)
			assertEquals("bad status", 'failure', json.status)
			assertEquals("wrong error code", '125', json.errorCode)
		}
		service.createUserWithoutTimezone(failureBut200, failure)
	}
	
}
