package com.affinistechnologies.jamrocksingles.jamrocksingles.models;

import java.util.*;

public class Attribute
{
	private String Id;
	public final String getId()
	{
		return Id;
	}
	public final void setId(String value)
	{
		Id = value;
	}

	private String Type;
	public final String getType()
	{
		return Type;
	}
	public final void setType(String value)
	{
		Type = value;
	}

	private Object Value;
	public final Object getValue()
	{
		return Value;
	}
	public final void setValue(Object value)
	{
		Value = value;
	}

	private ArrayList<Attribute> Attributes;
	public final ArrayList<Attribute> getAttributes()
	{
		return Attributes;
	}
	public final void setAttributes(ArrayList<Attribute> value)
	{
		Attributes = value;
	}
}