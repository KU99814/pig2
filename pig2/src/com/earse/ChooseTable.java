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

public class ChooseTable extends SurfaceView implements SurfaceHolder.Callback {
	Pig2Activity activity;
	Paint paint;
	
	Bitmap background;
	Bitmap hasClean;
	Bitmap noClean;
	Bitmap[] number = new Bitmap[10];
	
	int startX = 100;
	int startY = 300;
	
	int moveX = 130;
	int moveY = 130;
	
	ArrayList<String[]> al=new ArrayList<String[]>();
	
	public float plY=16;
	public int widths;
	public int heights;
	
	public ChooseTable(Pig2Activity activity) {
		super(activity);
		
		this.activity=activity;
		this.getHolder().addCallback(this);
		
		//更瓜
		
		InputStream backgroundIs = this.getResources().openRawResource(R.drawable.checktable);
		InputStream hasCleanIs = this.getResources().openRawResource(R.drawable.hasclean);
		InputStream noCleanIs = this.getResources().openRawResource(R.drawable.noclean);
		BitmapFactory.Options options=new BitmapFactory.Options();
	    options.inJustDecodeBounds = false;
		
		background=BitmapFactory.decodeStream(backgroundIs,null,options); //璉春
		hasClean=BitmapFactory.decodeStream(hasCleanIs,null,options);
		noClean=BitmapFactory.decodeStream(noCleanIs,null,options);
		
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
		//砞﹚礶ガ
		paint=new Paint();
		paint.setAntiAlias(true);
	 }
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
	 switch(e.getAction())
	 {
	  case MotionEvent.ACTION_DOWN:
		  float y = e.getY();
	      float x = e.getX();
       for(int i=0,j=0;i<(LevelMap.LMAP.length);i++)
       {
    	if(x>100+(i%5)*130 && x<100+(i%5)*130+100
    	   &&y>300+(j*130)&&y<300+(j*130)+100)
    	{
    	 String isOpen=al.get(i)[1];
    	 if(isOpen.equals("1"))
    	 {
    	  level=i;
    	  activity.toAnotherView(LOADING);
    	 }
    	}
    	if(i%5==0&&i!=0)
    	 j++;
       }
       
       if(x>500&&x<750&&y>950&&y<1050)
    	activity.toAnotherView(ENTER_MENU);
		 
		//status把计叫把酚盽计摸
       destoryB();	
	   break;
		case MotionEvent.ACTION_UP:break;
		}
		return true;
		
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{	
	 canvas.drawBitmap(background,0, 0, paint);
	 
	 for(int i=0;i<al.size();i++)
	 {
	  String nowLevel=al.get(i)[0];
	  String isOpen=al.get(i)[1];
 	  if(isOpen.equals("0"))
 	   canvas.drawBitmap(noClean,startX,startY, paint);
 	  else if(isOpen.equals("1"))
 	  {
 	   canvas.drawBitmap(hasClean,startX,startY, paint);
 	   for(int j=0;j<nowLevel.length();j++)
		{//盢–计じ酶籹
			char c=nowLevel.charAt(j);
			canvas.drawBitmap(number[c-'0'],startX+25-j*5,startY+25, paint);
		}
 	  }
 	 startX+=moveX;
	 }
	}
	
	public void destoryB()
	{
	 if(!background.isRecycled())
	 {
	  background.recycle();
	 }
	 if(!hasClean.isRecycled())
	 {
	  hasClean.recycle();
	 }
	 
	 if(!noClean.isRecycled())
	 {
	  noClean.recycle();
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
	
	public void surfaceChanged(SurfaceHolder holder, int format,
			         int width,int height) {
		// TODO Auto-generated method stub
		widths=width;
		heights=height;
		 Canvas canvas=holder.lockCanvas();
			try
			{
				synchronized(holder)
				{
					onDraw(canvas);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(canvas!=null)
				{
					holder.unlockCanvasAndPost(canvas);
				}
			}
	}
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
		
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub		
	}
}
