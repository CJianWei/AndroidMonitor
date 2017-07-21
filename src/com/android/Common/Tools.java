package com.android.Common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Tools {
	@SuppressWarnings("finally")
	public static String RunCmd(String cmd){
		StringBuilder tv_result = new StringBuilder("");
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			InputStream is = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = reader.readLine()) != null) {
				tv_result.append(line + "\n");
			}
			p.waitFor();
			is.close();
			reader.close();
		} catch (Exception e) {
			tv_result = new StringBuilder("");
		} finally {
			return tv_result.toString().replaceAll("\t", " ").replaceAll("\r", " ").trim();
		}
	}
	
	public static ArrayList toLines(String msg,String split){
		String[] strs = msg.split(split);
		ArrayList tmps = new ArrayList();
		for (int i=0;i<strs.length;i++){
			if (strs[i].trim().equals("") == false){
				tmps.add(strs[i].trim());
			}
		}
		return tmps; 
	}
	
	
}
