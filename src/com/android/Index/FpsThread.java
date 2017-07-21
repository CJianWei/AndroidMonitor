package com.android.Index;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.Common.Tools;

public class FpsThread extends BaseThread{
	
	public FpsThread(String udid,String pack) {
		super(udid,pack);
	}
	
	/*
	 * 计算渲染一帧需要多少脉冲，理论上一个脉冲就足够了
	 * */
	@Override
	public void Do(){
		ArrayList lines = Tools.toLines(this.adb.getFpsInfo(this.pack), "\n");
		int count = 0;
		for (int i= 0;i<lines.size();i++){
			String line = (String) lines.get(i);
			if (line.contains("Draw") && line.contains("Process") && line.contains("Execute")){
				count = Tools.toLines(line, " ").size();
				continue;
			}
			if (count > 0){
				ArrayList vs = Tools.toLines(line, " ");
				if (count == this.CountDouble(vs)){
					double rank = 0;
					double allCost = 0;
					for (int j= 0;j<vs.size();j++){
						allCost += Double.parseDouble((String)vs.get(j));	
					}
					if (allCost <= 16.67){
						rank = 1;
					}else{
						rank =(int) (allCost/16.67);
						if (allCost % 16.67 > 0){
							rank ++;
						}
					}
					Data.setV("fps", this.udid, rank);
				}
			}
			
		}
		
		
	}
	
	private int CountDouble(ArrayList vs){
		int count = 0;
		for (int i =0;i<vs.size();i++ ){
			String str = (String) vs.get(i);
			try{
				Double.parseDouble(str);
				count++;
			}catch(Exception e){
				continue;
			}
		}
		if (count == vs.size()){
			return count;
		}else{
			return 0;
		}
	}
}
