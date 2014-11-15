package com.kendelong.web.user

import javax.validation.Valid
import javax.validation.Validator

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import com.kendelong.domain.User
import com.kendelong.service.UserService
import com.kendelong.service.dao.UserRepository
import com.kendelong.service.security.HeaderBasedTokenRememberMeServices
import com.kendelong.web.WebServiceResponse

@RestController
class UserController
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder
	
	@Autowired
	private HeaderBasedTokenRememberMeServices rememberMeServices;
	
	@Autowired
	private Validator validator
	
	@Autowired
	private UserService userService

	@RequestMapping(value="/user/v1/create", method=RequestMethod.POST)
	public WebServiceResponse createUser(@RequestBody @Valid UserRegistrationBean registration, BindingResult errors)
	{
		if(errors.hasErrors()) throw new BindException(errors)
				
		User user = new User(email: registration.email, password: registration.password, timezone: registration.timezone, birthDate: registration.birthDate)
		user.password = passwordEncoder.encode(user.password)
		repo.save(user)
		
		String token = rememberMeServices.encodeToken(user.id.toString(), user.password)
		WebServiceResponseWithToken response = new WebServiceResponseWithToken(status: 'success', message: 'Successfully created user', payload: user, token: token)
		return response
	}
	
	@RequestMapping(value="/public/v1/login", method=RequestMethod.POST)
	public WebServiceResponse login(@RequestBody UserRegistrationBean registration, BindingResult errors)
	{
		if(errors.hasErrors()) throw new BindException(errors)
		
		User user = userService.login(registration.email, registration.password)
		if(!user) throw new IllegalArgumentException("Login failed");
		
		String token = rememberMeServices.encodeToken(user.id.toString(), user.password)
		WebServiceResponseWithToken response = new WebServiceResponseWithToken(status: 'success', message: 'Successful login', payload: user, token: token)
		return response
	}
	
	@RequestMapping("/user/v1/{id}")
	public WebServiceResponse showUser(@PathVariable Long id, User user)
	{
		if(!id.equals(user.id))
			throw new IllegalArgumentException("Id in request [$id] does not match authenticated user's id [${user.id}]")
		if(user)
			return new WebServiceResponse(status: 'success', message: 'User located', payload: user)
		else
			throw new IllegalArgumentException("No user found for id [$id]")
	}
	
	@RequestMapping(value="/user/v1/update", method=RequestMethod.POST)
	public WebServiceResponse updateUser(User user, @Validated(UserUpdateValidationGroup.class) @RequestBody UserRegistrationBean registration, BindingResult errors)
	{
		if(errors.hasErrors()) throw new BindException(errors)
		
		// Ugly, and detached persistent object.
		if(registration.birthDate) user.birthDate = registration.birthDate
		if(registration.email) user.email = registration.email
		if(registration.timezone) user.timezone = registration.timezone
		if(registration.password) user.password = passwordEncoder.encode(registration.password)
		user = repo.save(user)
		
		String token = rememberMeServices.encodeToken(user.id.toString(), user.password)
		WebServiceResponseWithToken response = new WebServiceResponseWithToken(status: 'success', message: 'Successful login', payload: user, token: token)
		return response
	}

	public static class WebServiceResponseWithToken extends WebServiceResponse
	{
		String token
	}
	
}
