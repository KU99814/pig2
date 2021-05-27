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

public class Pass extends View {
	Pig2Activity activity;
	Paint paint;
	Bitmap background;
	
	Canvas canvas;
	
	public Pass(Pig2Activity activity) {
		super(activity);
		
		this.activity=activity;
		
		//以下載入圖片
		InputStream backgroundIs = this.getResources().openRawResource(R.drawable.pass);
	    BitmapFactory.Options options=new BitmapFactory.Options();
	    options.inJustDecodeBounds = false;
		
		background=BitmapFactory.decodeStream(backgroundIs,null,options); //背景
		
		
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
		
		//返回主選單
		if(x>150&&x<630&&y>610&&y<710)
		{
		 activity.toAnotherView(ENTER_MENU);
		 destoryB();
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
	 canvas.drawBitmap(background,100, 150, paint);
	}
	
	public void destoryB()
	{
	 if(!background.isRecycled())
	 {
	  background.recycle();
	 }
	 System.gc();
	}
	
}