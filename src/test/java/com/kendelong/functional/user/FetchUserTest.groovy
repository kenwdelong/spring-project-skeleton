package com.kendelong.functional.user
import static org.junit.Assert.*

import org.junit.Test

import com.kendelong.functional.BaseTestClass

class FetchUserTest extends BaseTestClass 
{
	private CreateUserService_1 createUserService = new CreateUserService_1()
	private FetchUserService_2 fetchUserService = new FetchUserService_2()

	@Test
	public void testAccessGetUserWithToken() 
	{
		def data = createUserService.createUser()
		def success = { 
			resp, json ->
			assertEquals("Wrong id", data.user.id, json.payload.id)
			prettyPrint(json)
		}
		fetchUserService.fetchUser(data.user.id, data.token, success, standardFailure)
	}
	
	@Test
	public void testFetchUserDoesNotReturnPassword()
	{
		def data = createUserService.createUser()
		def success = {
			resp, json ->
			assertNull("Password is present", data.user.password)
		}
		fetchUserService.fetchUser(data.user.id, data.token, success, standardFailure)
	}

}
