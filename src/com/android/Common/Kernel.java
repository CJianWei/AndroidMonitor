package com.android.Common;

import java.util.ArrayList;

public class Kernel {
	
	protected String udid;
	protected String SystemType;
	
	
	protected int width;
	protected int height;
	protected int kernel;
	
	protected String phoneVersion;
	protected String phoneName;
	protected String phoneLogo;
	
	protected int Mem;
	
	public Kernel(String udid){
		this.udid = udid;
		this.SystemType = getCurrentSystem();
	}
	
	public String getCurrentSystem(){
		String name = System.getProperty("os.name");
		if (name.toLowerCase().contains("window")){
			return "window";
		}
		return "other";
	}
	
	@SuppressWarnings("finally")
	public String callAdb(String cmd){
		
		String cmdPre = "adb";
		if (this.udid.equals("") == false){
			cmdPre = String.format("%s -s %s ", cmdPre,this.udid);
		}else{
			cmdPre += " ";
		}
		cmd = cmdPre + cmd;
		return Tools.RunCmd(cmd);
	}
	
	
	public String getGrep(){
		String grep = "grep";
		if (this.SystemType.equals("window")){
			grep = "findstr";
		}
		return grep;
	}
	
	public String getPix(){
		return this.callAdb("shell wm size");
	}
	
	public String getKel(){
		return this.callAdb("shell cat /proc/cpuinfo");
	}
	
	public String getMenTotal(){
		return this.callAdb("shell cat /proc/meminfo ");
	}
	
	public String getModel(){
		return this.callAdb("shell cat /system/build.prop");
	}
	
	public void initKernel(){
		//pix
		{
			ArrayList lines = Tools.toLines(getPix(), "\n");
			for (int i=0;i<lines.size();i++){
				String line = (String)lines.get(i);
				if (line.contains("Physical size:")){
					ArrayList parts = Tools.toLines(line.replace("Physical size:", "").trim(), "x");
					if (parts.size() ==2){
						width = Integer.parseInt((String) parts.get(0));
						height = Integer.parseInt((String) parts.get(1));
						break;
					}
				}
			}
		}
		
		//ker
		{
			ArrayList lines = Tools.toLines(getKel(), "\n");
			int ker_tmp =0;
			for (int i=0;i<lines.size();i++){
				String line = (String)lines.get(i);
				if (line.contains("processor")){
					ArrayList parts = Tools.toLines(line, " ");
					if (parts.size() == 3 && parts.get(0).equals("processor")){
						ker_tmp ++;
					}
				}
			}
			kernel = ker_tmp;
		}
		
		//model
		{
			ArrayList lines = Tools.toLines(getModel(), "\n");
			for (int i=0;i<lines.size();i++){
				String line = (String)lines.get(i);
				ArrayList parts = Tools.toLines(line, "=");
				if (parts.size()==2){
					String first = (String)parts.get(0);
					String second = (String)parts.get(1);
					if (first.equals("ro.build.version.release")){
						phoneVersion = second;
					}
					if (first.equals("ro.product.model")){
						phoneName = second;
					}
					if (first.equals("ro.product.brand")){
						phoneLogo = second;
					}
				}
			}
		}
		
		//mem
		{
			ArrayList lines = Tools.toLines(getMenTotal(), "\n");
			for (int i=0;i<lines.size();i++){
				String line = (String)lines.get(i);
				if (line.contains("MemTotal:")){
					ArrayList parts = Tools.toLines(line, " ");
					if (parts.size() == 3 && parts.get(0).equals("MemTotal:")){
						Mem = Integer.parseInt((String) parts.get(1));
					}
				}
			}
		}
	}
	
	public void Str(){
		System.out.println("width-->"+width);
		System.out.println("height-->"+height);
		System.out.println("kernel-->"+kernel);
		System.out.println("phoneVersion-->"+phoneVersion);
		System.out.println("phoneName-->"+phoneName);
		System.out.println("phoneLogo-->"+phoneLogo);
		System.out.println("Mem-->"+Mem);
	}
	
}
