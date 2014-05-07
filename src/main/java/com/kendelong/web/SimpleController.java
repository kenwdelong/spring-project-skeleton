package com.kendelong.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kendelong.domain.User;
import com.kendelong.service.dao.UserRepository;

@RestController
/*
 * These are demo web services, just for fun
 */
public class SimpleController
{
	
	@Autowired
	private UserRepository repo;

	@RequestMapping("/hello")
	public String sayHello()
	{
		return "well hello there";
	}
	
	@RequestMapping(value="/user")
	public User createAndFetchUser()
	{
		User user = new User();
		user.setEmail(System.currentTimeMillis() + "test@test.com");
		user.setPassword("passMe");
		repo.save(user);
		User m = repo.findOne(user.getId());
		return m;
	}
	
	@RequestMapping(value="createUser", method=RequestMethod.POST)
	public User createUser(@RequestBody User user)
	{
		repo.save(user);
		User m = repo.findOne(user.getId());
		return m;
	}
	
	@RequestMapping("/user/update/{id}")
	public User updateUser(@PathVariable Long id)
	{
		User m = repo.findOne(id);
		m.setPassword("updated");
		repo.save(m);
		return m;
	}
	
	@RequestMapping("/public/user/{id}")
	public User showUser(@PathVariable Long id)
	{
		// Removing use of id, so that we don't have a security hole
		User m = repo.findOne(1L);
		return m;
	}
	
	@RequestMapping("/public/userws/{id}")
	public WebServiceResponse showUserInWs(@PathVariable Long id)
	{
		// Removing use of id, so that we don't have a security hole
		User user = repo.findOne(1L);
		WebServiceResponse webServiceResponse = new WebServiceResponse();
		webServiceResponse.setMessage("Success");
		webServiceResponse.setPayload(user);
		webServiceResponse.setStatus("success");
		return webServiceResponse;
	}

}
