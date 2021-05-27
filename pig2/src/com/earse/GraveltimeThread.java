package com.earse;

import static com.earse.GameConstant.*;

public class GraveltimeThread extends Thread
{
	Pig2Activity activity;
	int i;
	int j;
	
	public GraveltimeThread(int i,int j)
	{
	 this.i = i;
	 this.j = j;
	}
	
	public void run()
	{
		try{
		 Thread.sleep(1000);
		 MAP[i][j] = 7;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
	}
}

