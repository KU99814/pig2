package com.earse;


import java.io.InputStream;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import static com.earse.GameConstant.*;

public class Menuview extends View {
	Pig2Activity activity;
	Paint paint;
	Bitmap background;
	Bitmap td;
	
	public float Dx=0;
	public static float Dy=-200; //讓選單下移
	
	//開始按鈕位置
	public static float choserX0=100;
	public static float choserY0=560;
	//設定按鈕位置
	public static float choserX1=100;
	public static float choserY1=700;
	//紀錄
	public static float choserX2=100;
	public static float choserY2=800;
	//離開
	public static float choserX4=100;
	public static float choserY4=930;
	
	public float plY=16;
	public int widths;
	public int heights;
	
	Canvas canvas;
	
	ArrayList<String[]> al=new ArrayList<String[]>();
	
	public Menuview(Pig2Activity activity) {
		super(activity);
		
		this.activity=activity;
		
		//以下載入圖片
		InputStream backgroundIs = this.getResources().openRawResource(R.drawable.back);
		InputStream tdIs = this.getResources().openRawResource(R.drawable.d);
	    BitmapFactory.Options options=new BitmapFactory.Options();
	    options.inJustDecodeBounds = false;
		
		background=BitmapFactory.decodeStream(backgroundIs,null,options); //背景
		td=BitmapFactory.decodeStream(tdIs,null, options); //標題

		
		al=DBUtil.getResult();
		if(al.size()==0)
		{
		 for(int i=0;i<LevelMap.LMAP.length;i++)
   		 {
   		  int clean = 0;
   		  int open=0;
   		  int highscore = 1000;
   		  if(i==0)
   		  {
   		   open=1;
   		  }
   		  DBUtil.insert((i+1),open,highscore,clean);
   		 }
		 al=DBUtil.getResult();
		}
		
		//設定畫布
		paint=new Paint();
		paint.setAntiAlias(true);
	 }
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
	 switch(e.getAction())
	 {
	  case MotionEvent.ACTION_DOWN:
		int x=(int)e.getX();
		int y=(int)e.getY();
		
		//status參數請參照常數類別
			
		if(x>choserX0&&x<choserX0+320&&y>choserY0&&y<choserY0+80)
         status=0;
       else if(x>choserX1&&x<choserX1+320&&y>choserY1&&y<choserY1+80)
		{
	     status=1;
		}
	   else if(x>choserX2&&x<choserX2+320&&y>choserY2&&y<choserY2+120)
		{
	     status=2;
		}
	   else if(x>choserX4&&x<choserX4+320&&y>choserY4&&y<choserY4+80)
		{
		 status=4;
        }
	   switch(status)
		{
		 case 0:
		  activity.toAnotherView(ENTER_CHOOSE);	break;
		 case 1:
		  activity.toAnotherView(ENTER_SETTING);break;
		 case 2:
		  activity.toAnotherView(ENTER_RECORD); break;
         case 4:
          System.exit(0);break;
		}
	   destoryB();
			break;
		case MotionEvent.ACTION_UP:break;
		}
		return true;
		
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
	 super.onDraw(canvas);
	 canvas.drawBitmap(background,0, 0, paint);
	 canvas.drawBitmap(td,Dx,Dy,paint);
	 
	 moveThreeD();
	 invalidate();
	}
	
	public void destoryB()
	{
	 if(!background.isRecycled())
	 {
	  background.recycle();
	 }
	 if(!td.isRecycled())
	 {
		 td.recycle();
	 }
	 System.gc();
	}
	
	
	public void moveThreeD()
	{
		if(threadFlag)
		{
			Dy=Dy+10;
			if(Dy>=0)
			{
			 Dy=0;
			 threadFlag=false;
			}
			
		}
	}
}
