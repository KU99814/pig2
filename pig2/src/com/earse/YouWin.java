package com.earse;


import static com.earse.GameConstant.*;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class YouWin extends SurfaceView implements SurfaceHolder.Callback{
	
	Pig2Activity activity;
	int screenWidth = 500;//螢幕寬度
	int screenHeight = 600;//螢幕高度
	
	Bitmap uwin;	
	Bitmap ulose;
	Bitmap uback2;
	Bitmap utest;
	public YouWin(Pig2Activity activity) {
		super(activity);
		
		getHolder().addCallback(this);	
		
		this.activity=activity;		
		
		// TODO Auto-generated constructor stub
		InputStream uwinIs = this.getResources().openRawResource(R.drawable.win);
		InputStream uloseIs = this.getResources().openRawResource(R.drawable.lose);
		InputStream uback2Is = this.getResources().openRawResource(R.drawable.back2);
		InputStream utestIs = this.getResources().openRawResource(R.drawable.testwin);
	    BitmapFactory.Options options=new BitmapFactory.Options();
	    options.inJustDecodeBounds = false;
	    
	    uwin = BitmapFactory.decodeStream(uwinIs,null,options); 	
		ulose=BitmapFactory.decodeStream(uloseIs,null,options);
		uback2=BitmapFactory.decodeStream(uback2Is,null,options);
		utest=BitmapFactory.decodeStream(utestIs,null,options);
		seeScoreLeft=false;
		seeScoreTime=false;
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(loseFlag)
			{
			 if(x>100&&x<670&&y>350&&y<500)
			 {
			  if(isTest)
			  {
			   activity.toAnotherView(ENTER_TEST);
			  }
			  else
			   activity.toAnotherView(LOADING);
		      activity.initSound();
			 }
			 else if(x>100&&x<670&&y>600&&y<750)
			 {
			  if(isTest)
               {
                activity.gotoEditMapList();
                isTest = false;
               }
              else
			   activity.toAnotherView(ENTER_MENU);
			 }
			}
			if(winFlag)
			{
			 if(x>100&&x<670&&y>350&&y<500)
			 {
			  if(!isTest)
		      {
			   if(level>=LevelMap.LMAP.length-1)
			    {
				 activity.toAnotherView(ENTER_Pass);
			    }
			    else
			   {
			    level++;
			    activity.toAnotherView(LOADING);
			   }
			 }
			  else
			  {
			   activity.toAnotherView(ENTER_TEST);
			  }
			  activity.initSound();
			  }
			 else if(x>100&&x<670&&y>600&&y<750)
			 {
			  if(isTest)
	           {
				activity.gotoEditMapList();
		       }
		      else
			   activity.toAnotherView(ENTER_MENU);
			 }
			}
			destoryB();
			break;
		
	}
			
			
		return true;
		
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		canvas.drawBitmap(uback2,0,0, null);
		if(isTest)
		 canvas.drawBitmap(utest,0,0, null);	
		else
		{
		if(winFlag)
		 canvas.drawBitmap(uwin,0,0, null);   
	    if(loseFlag)
	     canvas.drawBitmap(ulose, 0, 0,null);
		}
	}
	
	public void destoryB()
	{
	 if(!uback2.isRecycled())
	 {
	  uback2.recycle();
	 }
	 if(!uwin.isRecycled())
	 {
	  uwin.recycle();
	 }
	 if(!ulose.isRecycled())
	 {
	  ulose.recycle();
	 }
	 if(!utest.isRecycled())
	 {
	  utest.recycle();
	 }
	 System.gc();
	}
	
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {}

	
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Canvas canvas = holder.lockCanvas();//獲取畫布
		try{
			synchronized(holder){
				onDraw(canvas);//繪製
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(canvas != null){
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}

