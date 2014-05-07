package com.kendelong.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.kendelong.IDateConstants

@Entity
@Table(name='users')
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
class User extends BaseEntity
{
	@Column(name="active")
	boolean active = true
	
	@Column(name='email')
	String email
	
	@JsonIgnore
	@Column(name='passwd')
	String password

	@Column(name="time_zone")
	TimeZone timezone
	
	@Column(name='birth_date')
	@JsonFormat(timezone=IDateConstants.TIMEZONE, pattern=IDateConstants.DATE_FORMAT)
	Date birthDate
	

}  

