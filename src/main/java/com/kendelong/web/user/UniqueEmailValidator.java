package com.kendelong.web.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.kendelong.domain.User;
import com.kendelong.service.dao.UserRepository;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>
{
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void initialize(UniqueEmail constraintAnnotation)
	{
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context)
	{
		if(StringUtils.isBlank(value)) return true;
		User user = userRepo.findByEmail(value);
		if(user == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
