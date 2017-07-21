package com.android.Index;

import java.util.ArrayList;

import com.android.Common.Tools;

public class CPUThread extends BaseThread{

	public CPUThread(String udid,String pack) {
		super(udid,pack);
	}
	
	@Override
	public void Do(){
		ArrayList lines = Tools.toLines(this.adb.getCpuInfo(this.pack),"\n");
		for (int i=0;i<lines.size();i++){
			ArrayList parts = Tools.toLines((String)lines.get(i)," ");
			if (parts.size() >= 2 && ((String) parts.get(1)).endsWith(String.format("/%s:", this.pack))){
				if (parts.size() >= 3 && ((String) parts.get(2)).endsWith("%")){
					Data.setV("cpu", this.udid, Double.parseDouble(((String) parts.get(2)).replace("%", "").trim()));
				}
			}
		}
	}
}
