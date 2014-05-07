package com.kendelong.web.user

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import javax.validation.groups.Default

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank

import com.fasterxml.jackson.annotation.JsonFormat
import com.kendelong.IDateConstants

class UserRegistrationBean
{
	@Email(groups=[Default.class,UserUpdateValidationGroup.class], message='100')
	@NotBlank(message='101')
	@UniqueEmail(groups=[Default.class,UserUpdateValidationGroup.class], message='102')
	String email
	
	@NotBlank(message='105')
	String password
	
	@NotNull(message='110')
	@JsonFormat(timezone=IDateConstants.TIMEZONE, pattern=IDateConstants.DATE_FORMAT)
	Date birthDate
	
	@Size(max=255, groups=[Default.class,UserUpdateValidationGroup.class],message='120')
	String name
	
	@NotNull(message='125')
	TimeZone timezone

}
