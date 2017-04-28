package com.affinistechnologies.jamrocksingles.jamrocksingles.models;

public class BirthDate
{
	public BirthDate(){

	}
	public BirthDate(int year,int month, int day){
		this.setYear(year);
		this.setMonth(month);
		this.setDay(day);
	}
	private int Day;
	public final int getDay()
	{
		return Day;
	}
	public final void setDay(int value)
	{
		Day = value;
	}
	private int Month;
	public final int getMonth()
	{
		return Month;
	}
	public final void setMonth(int value)
	{
		Month = value;
	}
	private int Year;
	public final int getYear()
	{
		return Year;
	}
	public final void setYear(int value)
	{
		Year = value;
	}
	public final void AddYears(int amount){
		setYear(getYear()+amount);
	}
}