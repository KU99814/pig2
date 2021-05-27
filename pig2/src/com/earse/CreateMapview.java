package com.earse;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.earse.GameConstant.*;

public class CreateMapview extends SurfaceView implements SurfaceHolder.Callback { 
	Pig2Activity activity;
	Paint paint;
	
	int mPreviousX;//上次的觸控位置Y座標
	int mPreviousY;//上次的觸控位置Y座標

	boolean putstartPoint = false;
	boolean putendPoint = false;
	
	float span=35f;//矩形大小
	
	Canvas canvas;
	
	DisplayMetrics dm = new DisplayMetrics();

	public CreateMapview(Pig2Activity activity) {
		super(activity);
		
		this.activity=activity;
		this.getHolder().addCallback(this);//設置生命週期回呼介面的實作者
		
		paint = new Paint();//建立畫筆
		paint.setAntiAlias(true);//打開抗
		
		starti = 0;
		startj = 0;
		
		MAP = new int[25][22]; //地圖記錄
		
		for(int i=0;i<MAP.length;i++)
		 for(int j=0;j<MAP[0].length;j++)
		 {
		  MAP[i][j] = 0;
		 }
		
	 }
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
	 int x=(int)e.getX();
	 int y=(int)e.getY();
	 if(isPaint)
	 switch(e.getAction())
	 {
	  case MotionEvent.ACTION_DOWN:
		
		//繪製地圖
		for(int i=0;i<MAP.length;i++){
		 for(int j=0;j<MAP[0].length;j++){
		  if(x>=2+j*(span+1) && x<2+j*(span+1)+span&&
			 y>=2+i*(span+1) && y<2+i*(span+1)+span)
		  {
		   if(MAP[i][j]!=0){
		    if(i == starti && j == startj&&putstartPoint)
			{
		     putstartPoint = false;
		    }
		    if(MAP[i][j]==2)
		     putendPoint = false;
 		    MAP[i][j]=0;	//設置畫筆顏色為黑色	
		   }
		   else if(MAP[i][j]==0)
		   {
		    if(nowPoint==1||nowPoint==4||nowPoint==5||nowPoint==6
		    ||nowPoint==8||nowPoint==9||nowPoint==10||nowPoint==11)
 		     MAP[i][j]=nowPoint;//設置畫筆顏色為白色
		    if(nowPoint==2)
		    {
		     starti= i;
			 startj= j;
	 		 MAP[i][j]=1;	//設置畫筆顏色為黑色
	 		 putstartPoint = true;
		    }
	 		if(nowPoint==3)
		    {
		     for(int i2=0;i2<MAP.length;i2++)
		   	  for(int j2=0;j2<MAP[0].length;j2++){
		   	   if(MAP[i2][j2]==2)
		   		MAP[i2][j2]=1;   
		   	  }
		     putendPoint = true;
	 		 MAP[i][j]=2;//設置畫筆顏色為白色	
		    }
		    if(nowPoint==7)
		    {
		     MAP[i][j]=3;
		    }
		    if(nowPoint==12)
		    {
		     MAP[i][j]=15;
		    }
		   }
		  }
		 }}
		
		break;
		case MotionEvent.ACTION_UP:break;
		}
	   mPreviousX = x;
	   mPreviousY = y;
	   repaint();
		return true;
		
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
	 super.onDraw(canvas);
		int row=MAP.length;//地圖行數
		int col=MAP[0].length;//地圖列數
		canvas.drawARGB(255, 128, 128, 128);//設置背景顏色
		
		for(int i=0;i<row;i++)//繪製地圖
		{
		 for(int j=0;j<col;j++){
		  if(MAP[i][j]==1){
		   paint.setColor(Color.BLACK);	//設置畫筆顏色為黑色
		   if(i ==starti&& j == startj&&putstartPoint)
			paint.setColor(Color.CYAN);	
		  }
		  else if(MAP[i][j]==0){
		   paint.setColor(Color.WHITE);//設置畫筆顏色為白色
		  }
		  else if(MAP[i][j]==2)
			paint.setColor(Color.RED);
		  else if(MAP[i][j]==3)
		   paint.setColor(Color.YELLOW);
		  else if(MAP[i][j]==4)
		   paint.setColor(Color.BLUE);
		  else if(MAP[i][j]==5)
		   paint.setColor(Color.GREEN);
		  else if(MAP[i][j]==6)
		   paint.setColor(Color.GRAY);
		  else if(MAP[i][j]==8)
		   paint.setColor(Color.rgb(0, 150, 0));
	      else if(MAP[i][j]==9)
	       paint.setColor(Color.rgb(255, 128, 255));
	      else if(MAP[i][j]==10)
		   paint.setColor(Color.rgb(128,64, 0));
	      else if(MAP[i][j]==11)
           paint.setColor(Color.rgb(128, 0, 64));
	      else if(MAP[i][j]==15)
	       paint.setColor(Color.rgb(0, 128,255));
		 canvas.drawRect(2+j*(span+1),2+i*(span+1),2+j*(span+1)+span,2+i*(span+1)+span, paint);//繪製矩形
		 }
		}
	 invalidate();
	}
	
	public void getMap(int[][] theMap,int i2,int j2)
	{
	for(int i=0;i<MAP.length;i++)
	 for(int j=0;j<MAP[0].length;j++){
	  MAP[i][j] = theMap[i][j];
	 }
	 starti = i2;
	 startj = j2;
	 putendPoint = true;
	 putstartPoint = true;
	 repaint();
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
	
	public void repaint()
	{
		Canvas canvas = getHolder().lockCanvas();//獲取畫布
		try{
			synchronized(getHolder()){
				onDraw(canvas);//繪製
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(canvas != null){
				getHolder().unlockCanvasAndPost(canvas);
			}
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
}
