package com.affinistechnologies.jamrocksingles.jamrocksingles.models;

public class Client extends Document
{
   private String Name;
   public final String getName()
   {
	   return Name;
   }
   public final void setName(String value)
   {
	   Name = value;
   }
}