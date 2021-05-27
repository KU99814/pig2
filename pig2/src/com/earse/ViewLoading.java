package com.earse;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class ViewLoading extends SurfaceView implements SurfaceHolder.Callback{

	Pig2Activity activity;//�ŧi�ܼ�
	Paint paint;//�e��
	Bitmap load;//���J�e���I����
	
	int process=0;//loading�i�׼лx�줸
	int pointNum=-1;//ø�s�I���ƶq���лx�줸 
	
	public ViewLoading(Pig2Activity activity) { 
		super(activity);
		// TODO Auto-generated constructor stub
		this.activity=activity;
		paint=new Paint();
		getHolder().addCallback(this);
		InputStream loadIs = this.getResources().openRawResource(R.drawable.load);
		BitmapFactory.Options options=new BitmapFactory.Options();
	    options.inJustDecodeBounds = false;
		
		load = BitmapFactory.decodeStream(loadIs,null,options); //���J�e��1
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
		Canvas canvas = holder.lockCanvas();//����e��
		try{
			synchronized(holder){
				onDraw(canvas);//ø�s
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

	//��ø����k
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

