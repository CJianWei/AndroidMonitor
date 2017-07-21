package com.android.Index;

import java.util.ArrayList;

import com.android.Common.Tools;

public class MemThread extends BaseThread{

	public MemThread(String udid, String pack) {
		super(udid, pack);
	}

	
	@Override
	public void Do(){
		ArrayList lines = Tools.toLines(this.adb.getMenInfo(this.pack),"\n");
		for (int i=0;i< lines.size();i++){
			if (((String) lines.get(i)).contains("TOTAL")){
				ArrayList parts = Tools.toLines((String)lines.get(i), " ");
				if (parts.size() >=2 && parts.get(0).equals("TOTAL")){
					Data.setV("mem", this.udid, Double.parseDouble((String) parts.get(1)));
				}
			}
			
		}
	}
}
