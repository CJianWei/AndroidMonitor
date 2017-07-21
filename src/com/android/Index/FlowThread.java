package com.android.Index;

import java.util.ArrayList;

import com.android.Common.Tools;

public class FlowThread extends BaseThread{
	private String type;
	public FlowThread(String udid,String pack,String type) {
		super(udid, pack);
		this.type =type;
	}
	
	@Override
	public void Do(){
		ArrayList lines = Tools.toLines(this.adb.getFlow(this.pack),"\n");
		for (int i= 0;i<lines.size();i++){
			String line = (String) lines.get(i);
			if ((this.type.equals("wifi") && line.contains("wlan0:"))||
					(this.type.equals("gprs") && line.contains("rmnet0:"))){
				System.out.println(line);
				ArrayList parts = Tools.toLines(line, " ");
				if (parts.size() >= 10){
					ArrayList tmp = new ArrayList();
					tmp.add(Double.parseDouble((String)parts.get(1)));
					tmp.add(Double.parseDouble((String)parts.get(9)));
					Data.setV("flow", this.udid, tmp);
				}
			}
		}
	}
}
