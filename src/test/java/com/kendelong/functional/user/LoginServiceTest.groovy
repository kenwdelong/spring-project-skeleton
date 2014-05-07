package com.kendelong.functional.user

import static org.junit.Assert.*

import org.junit.Test

import com.kendelong.functional.BaseTestClass

class LoginServiceTest extends BaseTestClass
{
	private LoginService_4 loginService = new LoginService_4()
	private CreateUserService_1 createUserService = new CreateUserService_1()
	private FetchUserService_2 fetchUserService = new FetchUserService_2()
	
	@Test
	public void testLogin()
	{
		def data = createUserService.createUser()
		def token, id
		def success = {
			resp, json ->
			assertEquals("bad response code", 200, resp.statusLine.statusCode)
			assertEquals("bad status", 'success', json.status)
			assertNotNull("no token", json.token)
			token = json.token
			id = json.payload.id
			prettyPrint(json)
		}
		loginService.login(data.user.email, data.originalPassword, success, standardFailure)
		
		success = {
			resp, json ->
			assertEquals("bad response code", 200, resp.statusLine.statusCode)
			assertEquals("bad status", 'success', json.status)
		}
		fetchUserService.fetchUser(id, token, success, standardFailure)

	}
	
	@Test
	public void testLoginWithBadPassword()
	{
		def data = createUserService.createUser()
		def token, id
		def failure = {
			resp ->
			assertEquals("bad response code", 409, resp.statusLine.statusCode)
		}
		loginService.login(data.user.email, 'nonsense', shouldHaveFailed, failure)		
	}

	@Test
	public void testLoginWithBadEmail()
	{
		def data = createUserService.createUser()
		def token, id
		def failure = {
			resp ->
			assertEquals("bad response code", 409, resp.statusLine.statusCode)
		}
		loginService.login('me@you.com', 'nonsense', shouldHaveFailed, failure)		
	}

}
