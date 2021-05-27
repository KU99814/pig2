package com.earse;

import static com.earse.GameConstant.*;

public class ScoreNumberThread extends Thread
{
	Pig2Activity activity;
	
	public ScoreNumberThread(Pig2Activity activity)
	{
		this.activity=activity;
		finishTime = deadtimes;
		finishLeft = left;
	}
	
	public void run()
	{
	 try {
	  Thread.sleep(1000);
	 } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		seeScoreTime=true;
		while(finishTime>0)
		{
		 try {
			 finishTime-=1;
			 score += 50;
			 Thread.sleep(10);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		 }
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		seeScoreLeft=true;
		while(finishLeft>0)
		{
		 try {
			 finishLeft-=1;
			 score += 500;
			 Thread.sleep(1000);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		 }
		DBUtil.getNewRecord();
		exitScoreTable=true;
	}
}

