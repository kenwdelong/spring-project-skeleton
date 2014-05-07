package com.kendelong.service.security;

import static org.junit.Assert.*
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

import com.kendelong.domain.User
import com.kendelong.service.dao.UserRepository

public class DatabaseUserDetailsServiceTest
{
	private DatabaseUserDetailsService service
	
	@Before
	public void setup()
	{
		service = new DatabaseUserDetailsService();
		service.userRepo = { new User(id: 1L, email: 'normal@gmail.com', password: 'pass') } as UserRepository
	}

	@Test
	public void testGoodId()
	{		
		UserDetails details = service.loadUserByUsername("1");
		assertNotNull(details);
	}
	
	@Test
	public void testNormalEmailHasRoleUser()
	{		
		UserDetails details = service.loadUserByUsername("1");
		assertTrue(details.authorities.contains(new SimpleGrantedAuthority('ROLE_USER')));
	}
	
	@Test
	public void testMydomainEmailHasRoleAdmin()
	{		
		service.userRepo = { new User(id: 1L, email: 'normal@mydomain.com', password: 'pass') } as UserRepository
		UserDetails details = service.loadUserByUsername("1");
		assertTrue(details.authorities.contains(new SimpleGrantedAuthority('ROLE_ADMIN')));
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void testInvalidIdThrowsException()
	{
		service.userRepo = { null } as UserRepository
		service.loadUserByUsername('200')
	}

	@Test(expected=UsernameNotFoundException.class)
	public void testInvalidNumberThrowsException()
	{
		service.loadUserByUsername('200.74')
	}
}
