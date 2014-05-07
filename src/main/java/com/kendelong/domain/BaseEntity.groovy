package com.kendelong.domain

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.Version

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.kendelong.IDateConstants

@MappedSuperclass
class BaseEntity
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id
	
	@Version
	@JsonIgnore
	Long version
	
	@Column(name='create_date', updatable=false)
	@JsonFormat(timezone=IDateConstants.TIMEZONE, pattern=IDateConstants.DATE_TIME_FORMAT)
	Date createDate
	
	// Database trigger keeps this up to date
	@Column(name='update_date', updatable=false)
	@JsonFormat(timezone=IDateConstants.TIMEZONE, pattern=IDateConstants.DATE_TIME_FORMAT)
	Date updateDate
	
	@PrePersist
	void setupCreateDate()
	{
		createDate = updateDate = new Date()
	}
	

}
