package com.affinistechnologies.jamrocksingles.jamrocksingles.models;

//C# TO JAVA CONVERTER TODO TASK: The C# 'new()' constraint has no equivalent in Java:
//ORIGINAL LINE: public class OperationResult<T> where T : new()
public class OperationResult<T>
{
	public OperationResult()
	{
		setResource(null);
	}

	public OperationResult(T resource, OpResultStatus statusCode, String message)
	{
		setResource(resource);

		setStatusCode(statusCode);

		setMessage(message);
	}

	private T Resource;

	public final T getResource()
	{
		return Resource;
	}

	public final void setResource(T value)
	{
		Resource = value;
	}

	private OpResultStatus StatusCode;
	public final OpResultStatus getStatusCode()
	{
		return StatusCode;
	}
	public final void setStatusCode(OpResultStatus value)
	{
		StatusCode = value;
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
}