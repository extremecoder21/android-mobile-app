package com.affinistechnologies.jamrocksingles.jamrocksingles.models;

public class Profile extends Document
{
	private String AccountId;
	public final String getAccountId()
	{
		return AccountId;
	}
	public final void setAccountId(String value)
	{
		AccountId = value;
	}
	private String Ethnicity;
	public final String getEthnicity()
	{
		return Ethnicity;
	}
	public final void setEthnicity(String value)
	{
		Ethnicity = value;
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
	public Location Location = new Location();
	public BirthDate BirthDate = new BirthDate();
}