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
public class RecordTable extends SurfaceView implements SurfaceHolder.Callback {
	
	Pig2Activity activity;
	Paint paint;
	Bitmap background;
	Bitmap[] number = new Bitmap[10];
	
	public final float X=50;
	public final float Y=120;
	
	public final float x=300;
	public final float y=930;
	
	ArrayList<String[]> al=new ArrayList<String[]>();
	
	Canvas canvas;
	
	public RecordTable(Pig2Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		this.activity=activity;
		
		InputStream backgroundIs = this.getResources().openRawResource(R.drawable.recordtable);
		BitmapFactory.Options options=new BitmapFactory.Options();
	    options.inJustDecodeBounds = false;
		background=BitmapFactory.decodeStream(backgroundIs,null,options);	
		
        InputStream[] numIs = new InputStream[10];
		
		numIs[0]=this.getResources().openRawResource(R.drawable.number0);
		numIs[1]=this.getResources().openRawResource(R.drawable.number1);
		numIs[2]=this.getResources().openRawResource(R.drawable.number2);
		numIs[3]=this.getResources().openRawResource(R.drawable.number3);
		numIs[4]=this.getResources().openRawResource(R.drawable.number4);
		numIs[5]=this.getResources().openRawResource(R.drawable.number5);
		numIs[6]=this.getResources().openRawResource(R.drawable.number6);
		numIs[7]=this.getResources().openRawResource(R.drawable.number7);
		numIs[8]=this.getResources().openRawResource(R.drawable.number8);
		numIs[9]=this.getResources().openRawResource(R.drawable.number9);
		
		for(int i=0;i<=9;i++)
		 number[i] = BitmapFactory.decodeStream(numIs[i],null,options);
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
			if(tempX>600 && tempX<750 && tempY>800 && tempY<930)
			{
			 level++;
			}
			
			//關閉聲音
			if(tempX>80 && tempX<250 && tempY>800 && tempY<930)
			{
			 level--;
			}
			
			if(level>=LevelMap.LMAP.length)
			 level=0;
			else if(level<0)
			 level = LevelMap.LMAP.length-1;
			
			//返回主選單
			if(tempX>x&&tempX<x+220&&tempY>y&&tempY<y+150)
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
     String sLevel = String.valueOf((level+1));
     for(int i=0;i<sLevel.length();i++)
		{//將每個數位字元繪製
			char c=sLevel.charAt(i);
			canvas.drawBitmap(number[c-'0'],380+(sLevel.length()-i)*5,350, paint);
		}
     
     String high = al.get(level)[2];
     
     for(int i=0;i<high.length();i++)
		{//將每個數位字元繪製
			char c=high.charAt(i);
			canvas.drawBitmap(number[c-'0'],380+(high.length()+i)*20,500, paint);
		}
	}
	
	public void destoryB()
	{
	 if(!background.isRecycled())
	 {
	  background.recycle();
	 }
	 for(int i=0;i<=9;i++)
	 {
	  if(!number[i].isRecycled())
	  {
	   number[i].recycle();
	  }
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


