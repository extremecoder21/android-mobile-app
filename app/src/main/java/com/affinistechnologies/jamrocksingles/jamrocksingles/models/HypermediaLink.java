package com.affinistechnologies.jamrocksingles.jamrocksingles.models;

import java.util.*;

public class HypermediaLink
{
	/** 
	 
	 
	 @param rel
	 @param methods
	 @param uri
	 @param links
	*/
	public HypermediaLink(String rel, ArrayList<String> methods, String uri, ArrayList<HypermediaLink> links)
	{
		this.setRel(rel);
		this.setMethods(methods);
		this.setUri(uri);
		this.setLinks(links);
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
   private String Rel;
   public final String getRel()
   {
	   return Rel;
   }
   public final void setRel(String value)
   {
	   Rel = value;
   }
   private ArrayList<String> Methods;
   public final ArrayList<String> getMethods()
   {
	   return Methods;
   }
   public final void setMethods(ArrayList<String> value)
   {
	   Methods = value;
   }
   private String Uri;
   public final String getUri()
   {
	   return Uri;
   }
   public final void setUri(String value)
   {
	   Uri = value;
   }
}