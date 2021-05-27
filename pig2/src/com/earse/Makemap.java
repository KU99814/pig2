package com.earse;


import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.earse.GameConstant.*;

public class Makemap extends SurfaceView implements SurfaceHolder.Callback {
	
	Pig2Activity activity;
	Paint paint;
	Bitmap makemap;
	Bitmap gameback;
	
	public final float x=450;
	public final float y=930;
	
	Canvas canvas;
	
	public Makemap(Pig2Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		this.activity=activity;
		
		DBUtil.createMapTable();
		
		InputStream makemapIs = this.getResources().openRawResource(R.drawable.makemap);
	    BitmapFactory.Options options=new BitmapFactory.Options();
	    options.inJustDecodeBounds = false;
		
	    makemap=BitmapFactory.decodeStream(makemapIs,null,options); //背景
	}

	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			float tempX=e.getX();
			float tempY=e.getY();
			
			//開始製作
			if(tempX>220&&tempX<220+400&&tempY>430&&tempY<430+100)
			{
			 activity.toCreateMap();
			}
			
			else if(tempX>220&&tempX<220+400&&tempY>630&&tempY<630+100)
			{
			 activity.gotoMapList();
			}
			
			//返回主選單
			else if(tempX>x&&tempX<x+320&&tempY>y&&tempY<y+80)
			{
			 activity.toAnotherView(ENTER_SETTING);
			}
			destoryB();
		break;
		case MotionEvent.ACTION_UP:	break;
		}
		return true;
		
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(makemap, 0, 0, null);
	}
	
	public void destoryB()
	{
	 if(!makemap.isRecycled())
	 {
		 makemap.recycle();
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

