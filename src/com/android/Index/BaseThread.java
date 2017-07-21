package com.android.Index;

import java.util.ArrayList;

import com.android.Common.Adb;


public class BaseThread extends Thread{
	protected String udid;
	protected String pack;
	protected Adb adb = null;
	protected boolean End = false;
	public BaseThread(String udid,String pack){
		this.End = false;
		this.udid = udid;
		this.pack= pack;
		this.adb = new Adb(udid);
	}
	
	public void EndThread(){
		this.End = true;
		Data.Wait(1000);
	}
	
	/*
	 * 定时执行某个操作，用于统计手机性能指标
	 * 
	 * */
	@Override
	public void run(){
		while(true){
			if (this.End){
				break;
			}
			Do();
			Data.Wait(1000);
			
		}
	}
	
	public void Do(){
		
	}
}

