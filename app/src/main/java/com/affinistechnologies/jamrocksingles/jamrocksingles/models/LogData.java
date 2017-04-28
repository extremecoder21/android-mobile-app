package com.affinistechnologies.jamrocksingles.jamrocksingles.models;

public class LogData extends Document
{
	private LogData Data;
	public final LogData getData()
	{
		return Data;
	}
	public final void setData(LogData value)
	{
		Data = value;
	}
	private String Message;
	public final String getMessage()
	{
		return Message;
	}
	public final void setMessage(String value)
	{
		Message = value;
	}
	private String StackTrace;
	public final String getStackTrace()
	{
		return StackTrace;
	}
	public final void setStackTrace(String value)
	{
		StackTrace = value;
	}
}