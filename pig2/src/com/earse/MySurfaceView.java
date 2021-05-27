package com.earse;

import static com.earse.GameConstant.*;
import static com.earse.LoadThreeDModel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class MySurfaceView extends GLSurfaceView {

    SceneRenderer mRenderer;//場景顯示器


    private float mPreviousX;//上次的觸控位置Y座標
    
    private float decision = 8; //距離
    
    static float direction=0.0f;//視線方向

    
    Pig2Activity activity=(Pig2Activity)this.getContext();
	
	
	public MySurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer();
        
        tX=-tempFlag+2*UNIT_SIZE;
        tZ=-tempFlag/2+0*UNIT_SIZE;
        cX = -tempFlag+(1)*UNIT_SIZE-1.5f;
        cY = 10f;
        cZ = 10f;
        
        setRenderer(mRenderer);				//設置顯示器		
        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//設置顯示模式為主動顯示   
    }
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		float y = e.getY();
        float x = e.getX();
        if(mRenderer.albfc != null)
        {
         mRenderer.albfc.vx=0;
		 mRenderer.albfc.vz=0;
        }
		switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			
			//按右上角顯示選單
			if(x>700 && x<800 &&y>0 && y<100 && !littleMenuNow && !winFlag)
			{
			 if(!littleMenustart)
			 {
			 littleMenuNow = true;
             littleMenustart = true;
             MenuDown = true;
			 THREAD_FLAG = false;
			 DEADTIME_FLAG=false;
			 } 
			}
			
			else if(littleMenuNow)
			{
			 if(!littleMenustart)
			 {
			  //繼續遊戲
			  if(x>200 && x<650 &&y>400&&y<500)
			  {
			   littleMenustart = true;
			   MenuUp = true;
			  }
			  //回主選單
			  else if(x>200 && x<650 &&y>550&&y<650)
			  { 
			   MENUY =2f;
			   littleMenuNow = false;
	           littleMenustart = false;
	           DEADTIME_RESET = true;
			   if(isTest)
			   {
                activity.gotoEditMapList();
			   }
               else
               {
			    activity.toAnotherView(ENTER_MENU);
               }
			  }
			 }
			}
			
			//獲勝時出現
			else if(winFlag && exitScoreTable)
			{
				DBUtil.UpdataLevel();
				
				if(x>200 && x<600 &&y>600 && y<800)
				{
				 
				 activity.toAnotherView(ENTER_WINVIEW);
				 exitScoreTable=false;
				}
				
				if(x>200 && x<600 &&y>830 && y<930)
				{
				 winFlag = false;
				 activity.stopSound();
			     activity.initSound();
				 activity.toAnotherView(START_GAME);
				 exitScoreTable=false;
				 
				}
			} 
	     break;
		case MotionEvent.ACTION_MOVE:  // 拖曳移動
            float dx = x - mPreviousX;//計算觸控筆Y位移
			   angleX -= dx/10;
			   if(angleX>360)
				   angleX-=360;
			   if(angleX<0)
				   angleX+=360;
			   
			   angleZ -= dx/10;
			   if(angleZ>360)
				   angleZ-=360;
			   if(angleZ<0)
				   angleZ+=360;
            // 設定 TextView 內容, 大小, 位置
            break;
		case MotionEvent.ACTION_UP:
			//ballVx+=0.1f;
			break;		
		}
        mPreviousX = x;//記錄觸控筆位置
        moveAngle = (angleX + angleZ)/2;
		return true;
	}
	

	class SceneRenderer implements GLSurfaceView.Renderer 
    {   
		//各種紋理Id
		int floorId;//地板紋理Id
		int cleanFloorId; //過關點紋理
		int replyPointId; //中繼點紋理
		int gravelId;
		int nitoId;
		int iceFloorId;
		int hasreplyId;
		
		
		
		int leftBall;
		int timeBall;
		
		int headId;//橫幅紋理Id
		int ballTextureId;//球紋理ID
		int backId;//背景圖Id
		int numberid; //數字Id
		
		int toolId;  //工具單Id
		int littleMenuId; //遊戲中選單Id
		int leftHeadId; //生命頭像Id
		int scoreTableId; //得分表Id
		
		
		
		

		//各種圖像
		FloorGroup floorGroup;//繪製地板的對象
		Tool allTool;
		
		TextureRect head;//工具列圖像
		TextureRect backGround; //背景圖像
		TextureRect tool;//選單按鈕的圖片
		TextureRect leftHead;//生命頭像圖片
		
		TextureRect littleMenu;//小選單圖片
		
		TextureRect scoreTable;//得分表圖片
		
		BallForDraw ball;//球
    	LogicalBall albfc;//物理球
    	//Ball_Go_Thread bgt;//球運動執行緒
    	
    	Deadtime deadtime; //倒數計時
    	
    	LeftNumber leftNumber; //命數
    	
    	
    	LevelNumber levelNumber;
    	ScoreNumber scoreNumber;//得分
    	ScoreLeft scoreLeft; //得分命數
    	ScoreTime scoreTime; //得分時間
    	
        public void onDrawFrame(GL10 gl) {
      
        	//採用平滑著色
            gl.glShadeModel(GL10.GL_SMOOTH);
            //設置為打開背面剪裁
    		gl.glEnable(GL10.GL_CULL_FACE);            
        	//清除顏色緩存於深度緩存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//設置當前矩陣為模式矩陣
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //設置當前矩陣為單位矩陣
            gl.glLoadIdentity(); 
            
          
           
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, -25);
            backGround.drawSelf(gl);
            gl.glPopMatrix();
            
    		
    		tX = albfc.currentX;
    		tZ = albfc.currentZ;
    		
    		//讓攝影機跟著球轉
    
    	   	 cZ=(float) (tZ+decision*Math.cos(Math.toRadians(angleZ)));
    	     cX=(float) (tX+decision*Math.sin(Math.toRadians(angleX)));     
    	    
    	    ballMove();//球運動
    	    
    	    littleMeunMove();//小選單動作
            
            GLU.gluLookAt
			(gl, //畫筆
			  //觀察點座標
			 cX, //X座標
			 cY, //Y座標 
			 cZ, //Z座標
			 //目標點座標
			 tX, //X
			 UNIT_HIGHT*6, //Y
			 tZ, //Z
			 //角度
			 0, //X
			 1, //Y
			 0 //Z
			);
            
            
            
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, -2);
            floorGroup.drawSelf(gl); //繪製地圖
            
            allTool.drawSelf(gl);
            albfc.drawSelf(gl); //繪製球
            gl.glPopMatrix();  
            
            
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            //開啟混合   
            gl.glEnable(GL10.GL_BLEND); 
            //設置源混合因數與目標混合因數
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

            gl.glTranslatef(0,8.475f*ratio, -15*ratio);
            head.drawSelf(gl);//繪製工具表

            gl.glTranslatef(5f*ratio, -0.15f*ratio, 0.030f*ratio);
            tool.drawSelf(gl);//繪製選單按鈕

           
            gl.glPushMatrix();
			gl.glTranslatef(-5.5f*ratio, 0f, 0f);
			deadtime.drawSelf(gl); //繪製時間
			gl.glPopMatrix();
			
			gl.glPushMatrix();
			gl.glTranslatef(-10f*ratio, 0f, 0f);
			leftHead.drawSelf(gl);//繪製命數頭像
			gl.glTranslatef(1*ratio, 0f, 0f);
			leftNumber.drawSelf(gl); //繪製命數
			gl.glPopMatrix();
			
			 gl.glPushMatrix();
			 gl.glTranslatef(-3, MENUY,  4*ratio);
			 littleMenu.drawSelf(gl); //繪製選單
			 gl.glPopMatrix();
			
			//勝利時出現
			if(winFlag && !isTest)
			{
			gl.glPushMatrix();
			gl.glTranslatef(0,0,2f*ratio);
			gl.glTranslatef(-5f*ratio, -8.5f*ratio, 0f);
			scoreTable.drawSelf(gl); //得分表
			gl.glTranslatef(0, 0, 0.030f*ratio);
			gl.glTranslatef(0, 4f*ratio, 0);
			levelNumber.drawSelf(gl);
			gl.glTranslatef(0, -1.2f*ratio, 0);
			if(seeScoreTime)
             scoreTime.drawSelf(gl); //剩餘時間
			gl.glTranslatef(0, -1.2f*ratio, 0);
			if(seeScoreLeft)
			 scoreLeft.drawSelf(gl); //剩餘命數
			gl.glTranslatef(0, -1.2f*ratio, 0);
			scoreNumber.drawSelf(gl); //得分
			gl.glPopMatrix();
           }
			
			
          }
    	
    	
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //設置視窗大小及位置 
        	gl.glViewport(0, 0, width, height);
        	//設置當前矩陣為投影矩陣
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //設置當前矩陣為單位矩陣
            gl.glLoadIdentity();
            //計算透視投影的比例
            ratio = (float) width / height;
            backWidth=width;
            backHeight=height;
            Log.d("backWidth"+backWidth, "backHeight"+backHeight);
            //呼叫此方法計算產生透視投影矩陣
           gl.glFrustumf(-ratio, ratio, -1, 1, 1.67f, 100);
           
           littleMenu=new TextureRect
                   (
                    2f,2f,
                    littleMenuId,
                    new float[]
                    {
                     1,0,0,0,0,1,
                     0,1,1,1,1,0
                    }
                   );   
           
           scoreTable=new TextureRect
                   (
                    2.5f,4.5f,
                    scoreTableId,
                    new float[]
                    {
                     1,0,0,0,0,1,
                     0,1,1,1,1,0
                    }
                   );
           
           
           leftHead=new TextureRect
                   (
                    0.4f,0.3f,
                    leftHeadId,
                    new float[]
                    {
                     1,0,0,0,0,1,
                     0,1,1,1,1,0
                    }
                   );
           
           tool=new TextureRect
                   (
                    0.5f,0.5f,
                    toolId,
                    new float[]
                    {
                     1,0,0,0,0,1,
                     0,1,1,1,1,0
                    }
                   );
           
           head=new TextureRect
            (
             ratio*12,ratio*0.75f,
             headId,
             new float[]
              {
               1,0,0,0,0,1,
               0,1,1,1,1,0
              }
            );
           
           
           
           backGround=new TextureRect
             (
              ratio*15,ratio*24,
              backId,
              new float[]
              {
               0.625f,0,0,0,0,0.93f,
               0,0.93f,0.625f,0.93f,0.625f,0 
              }
             );
           
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //關閉抗震動 
        	gl.glDisable(GL10.GL_DITHER);
        	//設置特定Hint專案的模式，這裡為設置為使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //設置螢幕背景色黑色RGBA
            gl.glClearColor(0,0,0,0);
        	//設置為打開背面剪裁
            gl.glEnable(GL10.GL_CULL_FACE);
            //啟用深度測試
            gl.glEnable(GL10.GL_DEPTH_TEST);               
            //初始化紋理
            
            floorId=initTexture(gl,R.drawable.floor); //一般地板
            cleanFloorId = initTexture(gl,R.drawable.cleanfloor); //過關地板
            replyPointId=initTexture(gl,R.drawable.relaypoint); //中繼地板
            gravelId = initTexture(gl,R.drawable.gravel); //中繼地板
            nitoId = initTexture(gl,R.drawable.nito); //土壤
            iceFloorId =  initTexture(gl,R.drawable.icefloor);
            hasreplyId = initTexture(gl,R.drawable.hasreply);
              
            leftBall = initTexture(gl,R.drawable.basketball);
    		timeBall = initTexture(gl,R.drawable.basketball);
            
            headId=initTexture(gl,R.drawable.head); //工具表
            toolId=initTexture(gl,R.drawable.l); //工具按鈕
            if(isTest)
             littleMenuId = initTexture(gl,R.drawable.testmenu); //小選單
            else
             littleMenuId = initTexture(gl,R.drawable.playmenu); //小選單
            
            leftHeadId=initTexture(gl,R.drawable.left); //命
            scoreTableId=initTexture(gl,R.drawable.scoretable); //得分表 
            numberid=initTexture(gl,R.drawable.number);//數字
            if(pigstate==0)ballTextureId=initTexture(gl,R.drawable.pigpicture);//初始化球紋理
            
            else if(pigstate==1)ballTextureId=initTexture(gl,R.drawable.pigpicture1);//初始化球紋理
            
            else if(pigstate==2)ballTextureId=initTexture(gl,R.drawable.pigpicture2);//初始化球紋理
            
            else if(pigstate==3)ballTextureId=initTexture(gl,R.drawable.pigpicture3);//初始化球紋理
            
            else if(pigstate==4)ballTextureId=initTexture(gl,R.drawable.pigpicture4);//初始化球紋理
            
            else if(pigstate==5)ballTextureId=initTexture(gl,R.drawable.pigpicture5);//初始化球紋理
            
            //3d模組圖片
            tree1Id=initTexture(gl,R.drawable.aaa);//tree
            tree2Id=initTexture(gl,R.drawable.bbb);//tree
            tree3Id=initTexture(gl,R.drawable.redtree1);//tree
            tree4Id=initTexture(gl,R.drawable.redtree2);//tree
            
            sugar1Id=initTexture(gl,R.drawable.sugar3);//糖 
            sugar2Id=initTexture(gl,R.drawable.sugar2);//
            
            meat1Id=initTexture(gl,R.drawable.meat1);//肉
            meat2Id=initTexture(gl,R.drawable.meat2);//
            
            stairId = initTexture(gl,R.drawable.gold);
            
            fanceId=initTexture(gl,R.drawable.rrrrr);//肉
            
            
            Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            if(mHour<6||mHour>18)
             backId=initTexture(gl,R.drawable.night);
             //backId=initTexture(gl,R.drawable.gameback);   //背景
            else if(mHour>=6&&mHour<=18)
             backId=initTexture(gl,R.drawable.gameback);
            	//backId=initTexture(gl,R.drawable.night);
            
            
            int[] allFloorId ={floorId,cleanFloorId,replyPointId,gravelId,nitoId,iceFloorId, hasreplyId};
            int[] allToolId = {leftBall,timeBall};
            
            Numbers number = new Numbers(numberid);
            
            INIT_I=LevelMap.startPoint[level][0];//所在的列  
            INIT_J=LevelMap.startPoint[level][1];//所在的行
            
            if(isTest)
            {
            	INIT_I=starti;//所在的列  
                INIT_J=startj;//所在的行
            }
            
            floorGroup=new FloorGroup(1,allFloorId); //地圖
            allTool = new Tool(allToolId);
            
            //建立球
    		ball=new BallForDraw(11.25f,SCALE,ballTextureId);
    		albfc = new LogicalBall(ball,-tempFlag+(INIT_J+1)*UNIT_SIZE-0.5f,
    				-tempFlag/2+(INIT_I+1)*UNIT_SIZE-0.5f,
    				0f,
    				0f,
    				activity,ballTextureId);
    		
    		deadtime=new Deadtime(number); //倒數
    	 	
    		leftNumber=new LeftNumber(number); //命數
    		
    		//得分
    		levelNumber =new LevelNumber(number);
    		scoreNumber=new ScoreNumber(number);
    		scoreTime=new ScoreTime(number);
            scoreLeft=new ScoreLeft(number);

        }
        
        public void ballMove()
    	{
    		if(GameConstant.THREAD_FLAG)
    		{
    		//控制球
             albfc.move();
    		if(albfc.state==3 || albfc.state==4)
    		{
    		 if(albfc.state == 3)
    			{
    			 albfc.state = 0;
    			 if(left==0)
    			 {
    			  THREAD_FLAG = false; //球運動停止
    			  DEADTIME_FLAG = false; //時間停止
    			  DEADTIME_RESET = true; //時間重設
                  loseFlag=true; //失敗
                  albfc.closeSound();
                  
                  activity.hd.sendEmptyMessage(ENTER_WINVIEW);
    			 }
    			 else
    			 {
                  left--;
                  albfc.restart();
    			 }
    			 }
    		 else if(albfc.state == 4)
    			{
                 THREAD_FLAG = false;
    			 DEADTIME_FLAG = false;
    			 winFlag=true;//獲勝
    			 DEADTIME_RESET = true;
    			 
    		     albfc.state = 0;
    		     albfc = new LogicalBall(ball,-tempFlag+(INIT_J+1)*UNIT_SIZE-0.5f,
    	    				-tempFlag/2+(INIT_I+1)*UNIT_SIZE-0.5f,
    	    				0f,
    	    				0f,
    	    				activity,ballTextureId);
    		     albfc.closeSound();
    		     if(isTest)
    		     {
    		      activity.toAnotherView(ENTER_WINVIEW);
    		     }
                 else
                 {
                  startScoreTable = true;
    		      new ScoreNumberThread(activity).start();
                 }
                }
    		}
    	}
       }
        public void littleMeunMove()
        {
        	if(MenuDown)
   		    {
   			 MENUY-=0.05f;
   			 if(MENUY<=-4f)
   			 {
   			  littleMenustart = false;
   			  MenuDown = false;
   			 }
   		    }
   		   //上升時
   		   if(MenuUp)
   		   {
   			 MENUY+=0.05f;
   			 if(MENUY>=2f)
   			 {
   			  littleMenustart = false;
   			  MenuUp = false;
   			  littleMenuNow = false;
   			  THREAD_FLAG = true;
   			  DEADTIME_FLAG=true;
			  new DeadtimeThread(activity).start();
   			 }
           }
        }
    }
	//初始化紋理
	public int initTexture(GL10 gl,int drawableId)//textureId
	{
		//產生紋理ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);    
		int currTextureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);
        InputStream is = this.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp; 
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        bitmapTmp.recycle(); 
        
        return currTextureId;
	}

	
	
}


