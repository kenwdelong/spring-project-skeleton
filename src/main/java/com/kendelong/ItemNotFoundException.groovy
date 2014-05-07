package com.kendelong;

public class ItemNotFoundException extends RuntimeException
{
	def id
	String type

	public ItemNotFoundException(Object id, Class clazz)
	{
		super();
		this.id = id;
		this.type = clazz.getSimpleName()
	}
}
