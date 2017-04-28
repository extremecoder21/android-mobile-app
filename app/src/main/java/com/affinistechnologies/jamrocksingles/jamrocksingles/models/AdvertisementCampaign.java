package com.affinistechnologies.jamrocksingles.jamrocksingles.models;

import java.util.*;

public class AdvertisementCampaign extends Document
{
	private String Titile;
	public final String getTitile()
	{
		return Titile;
	}
	public final void setTitile(String value)
	{
		Titile = value;
	}
	private String Description;
	public final String getDescription()
	{
		return Description;
	}
	public final void setDescription(String value)
	{
		Description = value;
	}
	public ArrayList<String> AdIds = new ArrayList<String>();
}