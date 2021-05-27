package com.earse;


import java.io.InputStream;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.earse.GameConstant.*;
public class Setting extends SurfaceView implements SurfaceHolder.Callback {
	
	Pig2Activity activity;
	Paint paint;
	Bitmap soundSet;
	Bitmap background;
	Bitmap setting;
	Bitmap choose;
	Bitmap noChoose;
	
	public final float X=50;
	public final float Y=120;
	
	public final float x=450;
	public final float y=930;
	
	Canvas canvas;
	
	ArrayList<String[]> al=new ArrayList<String[]>();
	
	public Setting(Pig2Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		this.activity=activity;
		InputStream soundSetIs = this.getResources().openRawResource(R.drawable.soundset);
		InputStream backgroundIs = this.getResources().openRawResource(R.drawable.back);
		InputStream settingIs = this.getResources().openRawResource(R.drawable.setting);
		InputStream chooseIs = this.getResources().openRawResource(R.drawable.pigchoose);
		InputStream noChooseIs = this.getResources().openRawResource(R.drawable.pignochoose);
		BitmapFactory.Options options=new BitmapFactory.Options();
	    options.inJustDecodeBounds = false;
		
		soundSet=BitmapFactory.decodeStream(soundSetIs,null, options);
		background=BitmapFactory.decodeStream(backgroundIs,null,options);
		setting=BitmapFactory.decodeStream(settingIs, null,options);
		choose=BitmapFactory.decodeStream(chooseIs,null,options);
		noChoose=BitmapFactory.decodeStream(noChooseIs, null,options);
		al=DBUtil.getResult();
	    
	}

	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			float tempX=e.getX();
			float tempY=e.getY();
			
			//開啟聲音
			if(tempX>450 && tempX<480 && tempY>400 && tempY<430)
			{
				soundFlag = true;
				if(!activity.mpBack.isPlaying())
				 activity.initSound();
			}
			
			//關閉聲音
			if(tempX>670 && tempX<700 && tempY>400 && tempY<430)
			{
				soundFlag = false;
				activity.stopSound();
			}
			
			//進入地圖製作
			if(tempX>330&&tempX<610&&tempY>550&&tempY<620)
			{
				activity.toAnotherView(ENTER_MAP);
				destoryB();
			}
			
			for(int i=0;i<=al.size();i++)
		     {
		      if(tempX>100+(i%3)*120 &&
		         tempX<100+(i%3)*120+130 &&
		         tempY>720+(i/3)*150 &&
		         tempY<720+(i/3)*150+150)//
		    	{
		    	 if(i==0)
		    	  pigstate=i;
		    	 else
		    	{
		    	  String isClean=al.get(i-1)[3];
		    	  if(isClean.equals("1"))
		    	  {
		    	   pigstate=i;
		    	  }
		    	 }
		    	}
		       }
			
			//返回主選單
			if(tempX>x&&tempX<x+320&&tempY>y&&tempY<y+80)
			{
			 activity.toAnotherView(ENTER_MENU);
			 destoryB();
			}
			rePaint();
		break;
		case MotionEvent.ACTION_UP:	break;
		}
		return true;
		
	}
	@Override
	public void onDraw(Canvas canvas)
	{

		canvas.drawBitmap(background, 0, 0, null);
		
		canvas.drawBitmap(setting, 0, 0, null);
		if(soundFlag)
		{
		 canvas.drawBitmap(soundSet, 450, 400, null);
		}
		if(!soundFlag)
		{
		 canvas.drawBitmap(soundSet, 685, 400, null);
		}
		
		canvas.drawBitmap(choose, 100+120*((int)(pigstate%3)), 720+150*(int)(pigstate/3), null);
		
		for(int i=1;i<=al.size();i++)
		{
		 String isClean=al.get(i-1)[3];
		 if(isClean.equals("0"))
 		  canvas.drawBitmap(noChoose, 100+120*(i%3), 720+150*(i/3), null);
		}
		}
	
	public void destoryB()
	{
	 if(!background.isRecycled())
	 {
	  background.recycle();
	 }
	 if(!setting.isRecycled())
	 {
	  setting.recycle();
	 }
	 if(!soundSet.isRecycled())
	 {
	  soundSet.recycle();
	 }
	 if(!choose.isRecycled())
	 {
	  choose.recycle();
	 }
	 if(!noChoose.isRecycled())
	 {
      noChoose.recycle();
	 }
	 System.gc();
	}
	
	
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void rePaint()
	{
		canvas=getHolder().lockCanvas();
		try
		{
			synchronized(getHolder())
			{
				onDraw(canvas);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(canvas!=null)
			{
				getHolder().unlockCanvasAndPost(canvas);
			}
		}
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		rePaint();
	}

	
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}


