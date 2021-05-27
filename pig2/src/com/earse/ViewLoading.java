package com.earse;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class ViewLoading extends SurfaceView implements SurfaceHolder.Callback{

	Pig2Activity activity;//宣告變數
	Paint paint;//畫筆
	Bitmap load;//載入畫面背景圖
	
	int process=0;//loading進度標誌位元
	int pointNum=-1;//繪製點的數量的標誌位元 
	
	public ViewLoading(Pig2Activity activity) { 
		super(activity);
		// TODO Auto-generated constructor stub
		this.activity=activity;
		paint=new Paint();
		getHolder().addCallback(this);
		InputStream loadIs = this.getResources().openRawResource(R.drawable.load);
		BitmapFactory.Options options=new BitmapFactory.Options();
	    options.inJustDecodeBounds = false;
		
		load = BitmapFactory.decodeStream(loadIs,null,options); //載入畫面1
	}
	
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);  
		canvas.drawBitmap(load, 0,0, paint);		
	}
	
	public void destoryB()
	{
	 if(!load.isRecycled())
	 {
	  load.recycle();
	 }
	 
	 System.gc();
	}

	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	
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

	//重繪的方法
	public void repaint()
	{
		Canvas canvas=this.getHolder().lockCanvas();
		try
		{
			synchronized(canvas)
			{
				onDraw(canvas);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(canvas!=null)
			{
				this.getHolder().unlockCanvasAndPost(canvas);
			}
		}
	}
}

