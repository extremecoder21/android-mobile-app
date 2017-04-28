package com.affinistechnologies.jamrocksingles.jamrocksingles.models;

import java.util.ArrayList;

public class Config
{
	private SimpleDateTime ExpireDate = new SimpleDateTime(0,0,0,0,0);
	public final SimpleDateTime getExpireDate()
	{
		return ExpireDate;
	}
	public final void setExpireDate(SimpleDateTime value)
	{
		ExpireDate = value;
	}
	private ArrayList<HypermediaLink> Links;
	public final ArrayList<HypermediaLink> getLinks()
	{
		return Links;
	}
	public final void setLinks(ArrayList<HypermediaLink> value)
	{
		Links = value;
	}
}