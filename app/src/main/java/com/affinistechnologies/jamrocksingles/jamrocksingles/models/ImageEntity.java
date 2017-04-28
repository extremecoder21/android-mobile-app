package com.affinistechnologies.jamrocksingles.jamrocksingles.models;

public class ImageEntity extends Document
{
	private int Width;
	public final int getWidth()
	{
		return Width;
	}
	public final void setWidth(int value)
	{
		Width = value;
	}
	private int Height;
	public final int getHeight()
	{
		return Height;
	}
	public final void setHeight(int value)
	{
		Height = value;
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
	private String Extension;
	public final String getExtension()
	{
		return Extension;
	}
	public final void setExtension(String value)
	{
		Extension = value;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] Data;
	private byte[] Data;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getData()
	public final byte[] getData()
	{
		return Data;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setData(byte[] value)
	public final void setData(byte[] value)
	{
		Data = value;
	}
	private String Name;
	public final String getName()
	{
		return Name;
	}
	public final void setName(String value)
	{
		Name = value;
	}
	private boolean IsCompressed;
	public final boolean getIsCompressed()
	{
		return IsCompressed;
	}
	public final void setIsCompressed(boolean value)
	{
		IsCompressed = value;
	}
	private boolean IsApproved;
	public final boolean getIsApproved()
	{
		return IsApproved;
	}
	public final void setIsApproved(boolean value)
	{
		IsApproved = value;
	}
	private boolean MarkedForDeletion;
	public final boolean getMarkedForDeletion()
	{
		return MarkedForDeletion;
	}
	public final void setMarkedForDeletion(boolean value)
	{
		MarkedForDeletion = value;
	}
	private boolean IsProfileImage;
	public final boolean getIsProfileImage()
	{
		return IsProfileImage;
	}
	public final void setIsProfileImage(boolean value)
	{
		IsProfileImage = value;
	}
}