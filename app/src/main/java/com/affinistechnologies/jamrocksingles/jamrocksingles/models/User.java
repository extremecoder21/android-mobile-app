package com.affinistechnologies.jamrocksingles.jamrocksingles.models;

import java.util.*;

public class User extends RestResource
{
	private String UserName;
	public final String getUserName()
	{
		return UserName;
	}
	public final void setUserName(String value)
	{
		UserName = value;
	}
	private String Password;
	public final String getPassword()
	{
		return Password;
	}
	public final void setPassword(String value)
	{
		Password = value;
	}
	private String Email;
	public final String getEmail()
	{
		return Email;
	}
	public final void setEmail(String value)
	{
		Email = value;
	}
	private String Gender;
	public final String getGender()
	{
		return Gender;
	}
	public final void setGender(String value)
	{
		Gender = value;
	}
	private BirthDate DateOfBirth;
	public final BirthDate getDateOfBirth()
	{
		return DateOfBirth;
	}
	public final void setDateOfBirth(BirthDate value)
	{
		DateOfBirth = value;
	}
	private String Token;
	public final String getToken()
	{
		return Token;
	}
	public final void setToken(String value)
	{
		Token = value;
	}
	private String Status;
	public final String getStatus()
	{
		return Status;
	}
	public final void setStatus(String value)
	{
		Status = value;
	}
	private String ProfileId;
	public final String getProfileId()
	{
		return ProfileId;
	}
	public final void setProfileId(String value)
	{
		ProfileId = value;
	}
	private boolean Certify;
	public final boolean getCertify()
	{
		return Certify;
	}
	public final void setCertify(boolean value)
	{
		Certify = value;
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