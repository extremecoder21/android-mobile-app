package com.affinistechnologies.jamrocksingles.jamrocksingles.models;

public class Document extends RestResource
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
	private SimpleDateTime CreatedOn = new SimpleDateTime(0,0,0,0,0);
	public final SimpleDateTime getCreatedOn()
	{
		return CreatedOn;
	}
	public final void setCreatedOn(SimpleDateTime value)
	{
		CreatedOn = value;
	}
	private SimpleDateTime UpdatedOn = new SimpleDateTime(0,0,0,0,0);
	public final SimpleDateTime getUpdatedOn()
	{
		return UpdatedOn;
	}
	public final void setUpdatedOn(SimpleDateTime value)
	{
		UpdatedOn = value;
	}
	private String UpdatedBy;
	public final String getUpdatedBy()
	{
		return UpdatedBy;
	}
	public final void setUpdatedBy(String value)
	{
		UpdatedBy = value;
	}
	private String CreatedBy;
	public final String getCreatedBy()
	{
		return CreatedBy;
	}
	public final void setCreatedBy(String value)
	{
		CreatedBy = value;
	}
	private String SkipId;
	public final String getSkipId()
	{
		return SkipId;
	}
	public final void setSkipId(String value)
	{
		SkipId = value;
	}
}