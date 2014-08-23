package com.owner.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owner.entity.Data;

public class GsonUtil {
	private static  Gson gson=new GsonBuilder().create();
	public synchronized static Gson getGson()
	{
		if(gson==null)
		{
			gson=new GsonBuilder().create();
		}
		return gson;
	}
	
	public static Data convertStringToData(String resultString)
	   {
		   Data data=getGson().fromJson(resultString, Data.class);
		   if(data!=null)
		   {
			   return data;
		   }
		   return null;
	   }
}
