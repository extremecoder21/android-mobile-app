package com.affinistechnologies.jamrocksingles.jamrocksingles.models;

import java.util.*;

public class RestResource
{
	public RestResource()
	{
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
	private ArrayList<String> ErrorMessages;
	public final ArrayList<String> getErrorMessages()
	{
		return ErrorMessages;
	}
	public final void setErrorMessages(ArrayList<String> value)
	{
		ErrorMessages = value;
	}
}