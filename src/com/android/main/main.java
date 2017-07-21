package com.android.main;

import com.android.Common.Adb;
import com.android.Common.Kernel;
import com.android.Index.BatteryThread;
import com.android.Index.CPUThread;
import com.android.Index.Data;
import com.android.Index.FlowThread;
import com.android.Index.FpsThread;
import com.android.Index.MemThread;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Adb.reStartAdb();
		Kernel ker = new Kernel("5d4ac087");
		ker.initKernel();
		ker.Str();
		CPUThread cpu = new CPUThread("5d4ac087","com.dy.kuxiao");
		cpu.start();
		MemThread mem = new MemThread("5d4ac087","com.dy.kuxiao");
		mem.start();
		FpsThread fps = new FpsThread("5d4ac087","com.dy.kuxiao");
		fps.start();
		BatteryThread battery = new BatteryThread("5d4ac087","com.dy.kuxiao");
		battery.start();
		FlowThread flow = new FlowThread("5d4ac087","com.dy.kuxiao","wifi");
		flow.start();
		Data.Wait(10000);
		cpu.EndThread();
		mem.EndThread();
		fps.EndThread();
		battery.EndThread();
		flow.EndThread();
		Data.toStr();
	}

}
