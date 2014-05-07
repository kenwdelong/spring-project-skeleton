package com.kendelong.service.security;

import org.apache.commons.lang.math.NumberUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

import com.kendelong.domain.User
import com.kendelong.service.dao.UserRepository

public class DatabaseUserDetailsService implements UserDetailsService
{
	@Autowired
	private UserRepository userRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		/*
		 * This is unusual. For the REST use cases, we use the user id as the username, because it's used to create
		 * the token, and if someone reverse engineered the token they'd find the username.  However, for the normal
		 * web login use cases, people are typing in their username, not id, so we have to watch for emails (non-numeric)
		 * and log them in with that.  This could be problematic, we might have to roll it back.
		 */
		User user = null
		if(NumberUtils.isNumber(username))
		{
			try 
			{
				Long id = Long.parseLong(username)
				user = userRepo.findOne(id)
			}
			catch(NumberFormatException e)
			{
				// it could be numeric but still fail
				String message = "Id [$username] is not a Long, cannot load user";
				logger.warn(message);
				throw new UsernameNotFoundException(message);
			}
		}
		else
		{
			user = userRepo.findByEmail(username)
		}		
		
		if(user == null)
		{
			throw new UsernameNotFoundException("No user found for [$username]");
		}
		
		def authorities = [new SimpleGrantedAuthority('ROLE_USER')]
		if(user.email.endsWith('mydomain.com')) authorities << new SimpleGrantedAuthority('ROLE_ADMIN')
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPassword(), authorities );
		return userDetails;
	}

}
