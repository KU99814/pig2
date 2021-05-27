package com.earse;

import static com.earse.GameConstant.*;

public class DeadtimeThread extends Thread
{
	Pig2Activity activity;
	
	public DeadtimeThread(Pig2Activity activity)
	{
		this.activity=activity;
	}
	
	public void run()
	{
		while(DEADTIME_FLAG)
		{
			if(deadtimes>0)
			{
				try {
					deadtimes-=1;
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
			if(deadtimes==0)
			{
				DEADTIME_FLAG=false;//關閉倒計時執行緒
				DEADTIME_RESET = true;
				THREAD_FLAG = false;
				loseFlag=true;
				activity.hd.sendEmptyMessage(ENTER_WINVIEW);
			}
		}				
	}
}

