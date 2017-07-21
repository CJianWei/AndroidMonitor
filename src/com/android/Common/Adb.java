package com.android.Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;



public class Adb extends Kernel{
	
	private String udid;
	private String SystemType;
	
	public Adb(String udid){
		super(udid);
	}

	public Adb(){
		this("");
	}
	
	public static boolean startAdb() {
		if (Tools.RunCmd("adb start-server").contains("successfully")){
			return true;
		}
		return false;
	}

	public static void stopAdb() {
		Tools.RunCmd("adb kill-server");
	}


	public static boolean reStartAdb() {
		stopAdb();
		return startAdb();
	}

	
	public HashMap<String,String > LoadDeviceMsg() {
		HashMap<String,String >deviceMap = new HashMap<String, String>();
		String cmd = "adb devices";
		String msg = this.callAdb("devices");
		String[] lines = msg.split("\n");
		for (int i = 0; i < lines.length; i++) {
			String tmp = lines[i].trim();
			if (tmp.startsWith("*") || tmp.startsWith("List")) {
				continue;
			}
			String[] ary_tmps = tmp.split("\t");
			if (ary_tmps.length != 2) {
				continue;
			}
			deviceMap.put(ary_tmps[0], ary_tmps[1]);
		}
		return deviceMap;
	}
	
	public boolean CheckDevices(){
		HashMap<String,String > deviceMap = this.LoadDeviceMsg();
		if (deviceMap.keySet().contains(this.udid)){
			if (deviceMap.get(this.udid).equals("devices")){
				return true;
			}
		}
		return false;
	}
	
	public String getState(){
		return this.callAdb("get-state").trim();
	}
		
	public void reboot(String option){
		String cmd = "reboot";
		if (option.equals("bootloader") || option.equals("recovery") ){
			cmd = String.format("%s %s", cmd,option);
		}
		this.callAdb(cmd);
	}
	
	public String pull(String remote,String local){
		return this.callAdb(String.format("pull %s %s", remote,local));
	}
	
	public String push(String local,String remote){
		return this.callAdb(String.format("push %s %s", local,remote));
	}
	
	public boolean openApp(String pack,String activity){
		String res = this.callAdb(String.format("shell am start -n %s/%s", pack,activity));
		if (res.toLowerCase().contains("error")){
			return false;
		}
		return true;
	}
	
	public String getProcessId(String pack){
		String res = this.callAdb(String.format("shell ps | grep %s",pack));
		if (res.equals("")){
			System.out.println(String.format("can not find pid by %s", pack));
			return "";
		}
		if (res.split(" ").length >= 5){
			return res.split(" ")[4];
		}
		return "";
		
	}
	
	public String getCpuInfo(String pack){
		return this.callAdb(String.format("shell dumpsys cpuinfo | grep %s",pack));
	}
	
	public String getMenInfo(String pack){
		return this.callAdb(String.format("shell dumpsys meminfo %s", pack));
	}
	
	public String getFpsInfo(String pack){
		return this.callAdb(String.format("shell dumpsys gfxinfo %s", pack));
	}
	
	public String getFlow(String pack){
		String cmd = String.format("shell cat /proc/%s/net/dev", this.getProcessId(pack));
		return this.callAdb(cmd);
	}
	
	public String getBattery(){
		return this.callAdb("shell dumpsys battery");
	}
}
