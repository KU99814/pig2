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

    SceneRenderer mRenderer;//������ܾ�


    private float mPreviousX;//�W����Ĳ����mY�y��
    
    private float decision = 8; //�Z��
    
    static float direction=0.0f;//���u��V

    
    Pig2Activity activity=(Pig2Activity)this.getContext();
	
	
	public MySurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer();
        
        tX=-tempFlag+2*UNIT_SIZE;
        tZ=-tempFlag/2+0*UNIT_SIZE;
        cX = -tempFlag+(1)*UNIT_SIZE-1.5f;
        cY = 10f;
        cZ = 10f;
        
        setRenderer(mRenderer);				//�]�m��ܾ�		
        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//�]�m��ܼҦ����D�����   
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
			
			//���k�W����ܿ��
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
			  //�~��C��
			  if(x>200 && x<650 &&y>400&&y<500)
			  {
			   littleMenustart = true;
			   MenuUp = true;
			  }
			  //�^�D���
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
			
			//��ӮɥX�{
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
		case MotionEvent.ACTION_MOVE:  // �즲����
            float dx = x - mPreviousX;//�p��Ĳ����Y�첾
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
            // �]�w TextView ���e, �j�p, ��m
            break;
		case MotionEvent.ACTION_UP:
			//ballVx+=0.1f;
			break;		
		}
        mPreviousX = x;//�O��Ĳ������m
        moveAngle = (angleX + angleZ)/2;
		return true;
	}
	

	class SceneRenderer implements GLSurfaceView.Renderer 
    {   
		//�U�د��zId
		int floorId;//�a�O���zId
		int cleanFloorId; //�L���I���z
		int replyPointId; //���~�I���z
		int gravelId;
		int nitoId;
		int iceFloorId;
		int hasreplyId;
		
		
		
		int leftBall;
		int timeBall;
		
		int headId;//��T���zId
		int ballTextureId;//�y���zID
		int backId;//�I����Id
		int numberid; //�ƦrId
		
		int toolId;  //�u���Id
		int littleMenuId; //�C�������Id
		int leftHeadId; //�ͩR�Y��Id
		int scoreTableId; //�o����Id
		
		
		
		

		//�U�عϹ�
		FloorGroup floorGroup;//ø�s�a�O����H
		Tool allTool;
		
		TextureRect head;//�u��C�Ϲ�
		TextureRect backGround; //�I���Ϲ�
		TextureRect tool;//�����s���Ϥ�
		TextureRect leftHead;//�ͩR�Y���Ϥ�
		
		TextureRect littleMenu;//�p���Ϥ�
		
		TextureRect scoreTable;//�o����Ϥ�
		
		BallForDraw ball;//�y
    	LogicalBall albfc;//���z�y
    	//Ball_Go_Thread bgt;//�y�B�ʰ����
    	
    	Deadtime deadtime; //�˼ƭp��
    	
    	LeftNumber leftNumber; //�R��
    	
    	
    	LevelNumber levelNumber;
    	ScoreNumber scoreNumber;//�o��
    	ScoreLeft scoreLeft; //�o���R��
    	ScoreTime scoreTime; //�o���ɶ�
    	
        public void onDrawFrame(GL10 gl) {
      
        	//�ĥΥ��Ƶۦ�
            gl.glShadeModel(GL10.GL_SMOOTH);
            //�]�m�����}�I���ŵ�
    		gl.glEnable(GL10.GL_CULL_FACE);            
        	//�M���C��w�s��`�׽w�s
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//�]�m��e�x�}���Ҧ��x�}
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //�]�m��e�x�}�����x�}
            gl.glLoadIdentity(); 
            
          
           
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, -25);
            backGround.drawSelf(gl);
            gl.glPopMatrix();
            
    		
    		tX = albfc.currentX;
    		tZ = albfc.currentZ;
    		
    		//����v����۲y��
    
    	   	 cZ=(float) (tZ+decision*Math.cos(Math.toRadians(angleZ)));
    	     cX=(float) (tX+decision*Math.sin(Math.toRadians(angleX)));     
    	    
    	    ballMove();//�y�B��
    	    
    	    littleMeunMove();//�p���ʧ@
            
            GLU.gluLookAt
			(gl, //�e��
			  //�[���I�y��
			 cX, //X�y��
			 cY, //Y�y�� 
			 cZ, //Z�y��
			 //�ؼ��I�y��
			 tX, //X
			 UNIT_HIGHT*6, //Y
			 tZ, //Z
			 //����
			 0, //X
			 1, //Y
			 0 //Z
			);
            
            
            
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, -2);
            floorGroup.drawSelf(gl); //ø�s�a��
            
            allTool.drawSelf(gl);
            albfc.drawSelf(gl); //ø�s�y
            gl.glPopMatrix();  
            
            
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            //�}�ҲV�X   
            gl.glEnable(GL10.GL_BLEND); 
            //�]�m���V�X�]�ƻP�ؼвV�X�]��
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

            gl.glTranslatef(0,8.475f*ratio, -15*ratio);
            head.drawSelf(gl);//ø�s�u���

            gl.glTranslatef(5f*ratio, -0.15f*ratio, 0.030f*ratio);
            tool.drawSelf(gl);//ø�s�����s

           
            gl.glPushMatrix();
			gl.glTranslatef(-5.5f*ratio, 0f, 0f);
			deadtime.drawSelf(gl); //ø�s�ɶ�
			gl.glPopMatrix();
			
			gl.glPushMatrix();
			gl.glTranslatef(-10f*ratio, 0f, 0f);
			leftHead.drawSelf(gl);//ø�s�R���Y��
			gl.glTranslatef(1*ratio, 0f, 0f);
			leftNumber.drawSelf(gl); //ø�s�R��
			gl.glPopMatrix();
			
			 gl.glPushMatrix();
			 gl.glTranslatef(-3, MENUY,  4*ratio);
			 littleMenu.drawSelf(gl); //ø�s���
			 gl.glPopMatrix();
			
			//�ӧQ�ɥX�{
			if(winFlag && !isTest)
			{
			gl.glPushMatrix();
			gl.glTranslatef(0,0,2f*ratio);
			gl.glTranslatef(-5f*ratio, -8.5f*ratio, 0f);
			scoreTable.drawSelf(gl); //�o����
			gl.glTranslatef(0, 0, 0.030f*ratio);
			gl.glTranslatef(0, 4f*ratio, 0);
			levelNumber.drawSelf(gl);
			gl.glTranslatef(0, -1.2f*ratio, 0);
			if(seeScoreTime)
             scoreTime.drawSelf(gl); //�Ѿl�ɶ�
			gl.glTranslatef(0, -1.2f*ratio, 0);
			if(seeScoreLeft)
			 scoreLeft.drawSelf(gl); //�Ѿl�R��
			gl.glTranslatef(0, -1.2f*ratio, 0);
			scoreNumber.drawSelf(gl); //�o��
			gl.glPopMatrix();
           }
			
			
          }
    	
    	
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�]�m�����j�p�Φ�m 
        	gl.glViewport(0, 0, width, height);
        	//�]�m��e�x�}����v�x�}
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //�]�m��e�x�}�����x�}
            gl.glLoadIdentity();
            //�p��z����v�����
            ratio = (float) width / height;
            backWidth=width;
            backHeight=height;
            Log.d("backWidth"+backWidth, "backHeight"+backHeight);
            //�I�s����k�p�ⲣ�ͳz����v�x�}
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
            //�����ܾ_�� 
        	gl.glDisable(GL10.GL_DITHER);
        	//�]�m�S�wHint�M�ת��Ҧ��A�o�̬��]�m���ϥΧֳt�Ҧ�
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //�]�m�ù��I����¦�RGBA
            gl.glClearColor(0,0,0,0);
        	//�]�m�����}�I���ŵ�
            gl.glEnable(GL10.GL_CULL_FACE);
            //�ҥβ`�״���
            gl.glEnable(GL10.GL_DEPTH_TEST);               
            //��l�Ư��z
            
            floorId=initTexture(gl,R.drawable.floor); //�@��a�O
            cleanFloorId = initTexture(gl,R.drawable.cleanfloor); //�L���a�O
            replyPointId=initTexture(gl,R.drawable.relaypoint); //���~�a�O
            gravelId = initTexture(gl,R.drawable.gravel); //���~�a�O
            nitoId = initTexture(gl,R.drawable.nito); //�g�[
            iceFloorId =  initTexture(gl,R.drawable.icefloor);
            hasreplyId = initTexture(gl,R.drawable.hasreply);
              
            leftBall = initTexture(gl,R.drawable.basketball);
    		timeBall = initTexture(gl,R.drawable.basketball);
            
            headId=initTexture(gl,R.drawable.head); //�u���
            toolId=initTexture(gl,R.drawable.l); //�u����s
            if(isTest)
             littleMenuId = initTexture(gl,R.drawable.testmenu); //�p���
            else
             littleMenuId = initTexture(gl,R.drawable.playmenu); //�p���
            
            leftHeadId=initTexture(gl,R.drawable.left); //�R
            scoreTableId=initTexture(gl,R.drawable.scoretable); //�o���� 
            numberid=initTexture(gl,R.drawable.number);//�Ʀr
            if(pigstate==0)ballTextureId=initTexture(gl,R.drawable.pigpicture);//��l�Ʋy���z
            
            else if(pigstate==1)ballTextureId=initTexture(gl,R.drawable.pigpicture1);//��l�Ʋy���z
            
            else if(pigstate==2)ballTextureId=initTexture(gl,R.drawable.pigpicture2);//��l�Ʋy���z
            
            else if(pigstate==3)ballTextureId=initTexture(gl,R.drawable.pigpicture3);//��l�Ʋy���z
            
            else if(pigstate==4)ballTextureId=initTexture(gl,R.drawable.pigpicture4);//��l�Ʋy���z
            
            else if(pigstate==5)ballTextureId=initTexture(gl,R.drawable.pigpicture5);//��l�Ʋy���z
            
            //3d�ҲչϤ�
            tree1Id=initTexture(gl,R.drawable.aaa);//tree
            tree2Id=initTexture(gl,R.drawable.bbb);//tree
            tree3Id=initTexture(gl,R.drawable.redtree1);//tree
            tree4Id=initTexture(gl,R.drawable.redtree2);//tree
            
            sugar1Id=initTexture(gl,R.drawable.sugar3);//�} 
            sugar2Id=initTexture(gl,R.drawable.sugar2);//
            
            meat1Id=initTexture(gl,R.drawable.meat1);//��
            meat2Id=initTexture(gl,R.drawable.meat2);//
            
            stairId = initTexture(gl,R.drawable.gold);
            
            fanceId=initTexture(gl,R.drawable.rrrrr);//��
            
            
            Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            if(mHour<6||mHour>18)
             backId=initTexture(gl,R.drawable.night);
             //backId=initTexture(gl,R.drawable.gameback);   //�I��
            else if(mHour>=6&&mHour<=18)
             backId=initTexture(gl,R.drawable.gameback);
            	//backId=initTexture(gl,R.drawable.night);
            
            
            int[] allFloorId ={floorId,cleanFloorId,replyPointId,gravelId,nitoId,iceFloorId, hasreplyId};
            int[] allToolId = {leftBall,timeBall};
            
            Numbers number = new Numbers(numberid);
            
            INIT_I=LevelMap.startPoint[level][0];//�Ҧb���C  
            INIT_J=LevelMap.startPoint[level][1];//�Ҧb����
            
            if(isTest)
            {
            	INIT_I=starti;//�Ҧb���C  
                INIT_J=startj;//�Ҧb����
            }
            
            floorGroup=new FloorGroup(1,allFloorId); //�a��
            allTool = new Tool(allToolId);
            
            //�إ߲y
    		ball=new BallForDraw(11.25f,SCALE,ballTextureId);
    		albfc = new LogicalBall(ball,-tempFlag+(INIT_J+1)*UNIT_SIZE-0.5f,
    				-tempFlag/2+(INIT_I+1)*UNIT_SIZE-0.5f,
    				0f,
    				0f,
    				activity,ballTextureId);
    		
    		deadtime=new Deadtime(number); //�˼�
    	 	
    		leftNumber=new LeftNumber(number); //�R��
    		
    		//�o��
    		levelNumber =new LevelNumber(number);
    		scoreNumber=new ScoreNumber(number);
    		scoreTime=new ScoreTime(number);
            scoreLeft=new ScoreLeft(number);

        }
        
        public void ballMove()
    	{
    		if(GameConstant.THREAD_FLAG)
    		{
    		//����y
             albfc.move();
    		if(albfc.state==3 || albfc.state==4)
    		{
    		 if(albfc.state == 3)
    			{
    			 albfc.state = 0;
    			 if(left==0)
    			 {
    			  THREAD_FLAG = false; //�y�B�ʰ���
    			  DEADTIME_FLAG = false; //�ɶ�����
    			  DEADTIME_RESET = true; //�ɶ����]
                  loseFlag=true; //����
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
    			 winFlag=true;//���
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
   		   //�W�ɮ�
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
	//��l�Ư��z
	public int initTexture(GL10 gl,int drawableId)//textureId
	{
		//���ͯ��zID
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


