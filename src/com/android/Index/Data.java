package com.android.Index;

import java.util.ArrayList;
import java.util.HashMap;

public class Data{
	public static HashMap<String,ArrayList> data = new HashMap<String,ArrayList>();
	
	public static boolean isExists(String key){
		if (data.keySet().contains(key)){
			return true;
		}
		data.put(key, new ArrayList());
		return true;
	}
	
	public static void setV(String key,String udid,Object d){
		key = String.format("%s_%s", udid,key);
		if (isExists(key)){
			ArrayList vs = data.get(key);
			vs.add(d);
			data.put(key, vs);
		}
	}
	
	public static ArrayList getV(String key,String udid){
		key = String.format("%s_%s", udid,key);
		isExists(key);
		return data.get(key);
	}
	
	public static void Wait(long wait){
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
		}
	}
	
	public static void toStr(){
		System.out.println(data.toString());
	}
	
}
