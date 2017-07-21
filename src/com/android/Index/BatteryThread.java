package com.android.Index;

import java.util.ArrayList;
import com.android.Common.Tools;

public class BatteryThread extends BaseThread{

	public BatteryThread(String udid,String pack) {
		super(udid,pack);
	}
	
	@Override
	public void Do(){
		ArrayList lines = Tools.toLines(this.adb.getBattery(),"\n");
		for (int i=0;i<lines.size();i++){
			String line = (String) lines.get(i);
			if (line.contains("level:")){
				ArrayList parts = Tools.toLines(line, " ");
				if (parts.size() == 2 && parts.get(0).equals("level:")){
					Data.setV("battery", this.udid, Double.parseDouble((String) parts.get(1)));
				}
			}
		}
	}
}
