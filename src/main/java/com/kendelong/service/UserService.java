package com.kendelong.service;

import javax.transaction.Transactional;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kendelong.domain.User;
import com.kendelong.service.dao.UserRepository;

@Service
@Transactional
public class UserService
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User login(String email, String password)
	{
		User user = userRepo.findByEmail(email);
		if(user == null) 
		{
			logger.debug("Cannot find user with email [" + email + "]");
			return null;
		}
		if(!passwordEncoder.matches(password, user.getPassword())) 
		{
			logger.debug("Passwords don't match for user with email [" + email + "]");
			return null;
		}
		else
		{
			return user;
		}

	}
	
}
