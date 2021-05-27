package com.earse;

import static com.earse.GameConstant.*;

import javax.microedition.khronos.opengles.GL10;

public class LogicalBall{
	
	float vx;//�y�bX�b��V���t��
	float vy;//�y�bY�b��V���t��
	float vz;//�y�bZ�b��V���t��
	
	float maxV = 6.5f;   //�̤j�t��
	float divA = 4000; //�[�t�װ��� 
	
	float dx = 0;
	float dz = 0;

	float vyMax=0;
	float currentVy;
	float timeDown=0;
	
	float timeLive=0;//�y���B���`�ɶ�
	
	BallForDraw ball;	//�y
	
	//�y���_�l��m
	float startX;
	float startY = 0.6f;
	float startZ;
	
	//��e��m
	float currentX;
	float currentY;
	float currentZ;
	
	//�W�@�ɶ���m
	float previousX;
	float previousY;
	float previousZ;	
	
	//�y����
	float xAngle=0;
	float zAngle=0;
	
	int state=0;//�y���B�涥�q  0-�b�O�W�B��  1-�����U��  2-���`�Ӧ^�ϼu  3-�X��  4-���
	
	int rows=MAP.length;
    int cols=MAP[0].length;
	
	Pig2Activity activity;
	
	int ballTextureId;
	
	boolean isBig = false;
	boolean isSmall = false;
	float ballrate = 1f;
	
	public LogicalBall(BallForDraw ball,float startX,float startZ,float startVx,float startVz,
			Pig2Activity activity,int ballTextureId)
	{
		this.activity = activity;
		this.ball=ball;
		this.startX=startX;
		this.startZ=startZ;

		vx = startVx;
		vz = startVz;
		
		currentX=startX; 
		currentY=startY;
		currentZ=startZ;
		
		previousX=startX; 
		previousY=startY;
		previousZ=startZ;
		
		this.ballTextureId = ballTextureId;
	}
	
	//ø�s�y
	public void drawSelf(GL10 gl)
	{
		gl.glEnable(GL10.GL_BLEND);//�}�ҲV�X
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0);//�N�y���վ�쥭�O�W
		gl.glTranslatef(currentX, currentY, currentZ);
		gl.glRotatef(xAngle, 0, 0, 1);
		gl.glRotatef(zAngle, 1, 0, 0);
		ball.drawSelf(gl);		
		gl.glPopMatrix();		
	}

	
	public void move()
	{
		float aTotal=(float)Math.sqrt(rx*rx+rz*rz);
		
		 //���`�t�פp���H�Ȯɻ{���y����F
		 if(aTotal/divA<MIN_SPEED)
		 {
		  rx=0;
		  rz=0;
		 }
		
	 vx+=rx/(divA);
	 if(vx>maxV)
	  vx=maxV;
     if(vx<-maxV)
	  vx=-maxV;
     
     vz+=rz/(divA);
     if(vz>maxV)
   	  vz=maxV;
     if(vz<-maxV)
   	  vz=-maxV;
     
	float vTotal=(float)Math.sqrt(vx*vx+vz*vz);
	
	 //���`�t�פp���H�Ȯɻ{���y����F
	 if(vTotal<MIN_SPEED)
	 {
	  vx=0;
	  vz=0;
	 }
	 
	//�y�B��

	 
	 
	
    //�y��� 
    xAngle=(float)Math.toDegrees(currentX/SCALE);
    zAngle=(float)Math.toDegrees(currentZ/SCALE);
    
    
		
		if(state==0)
		{//�b�O�W����
		
		 if(vx>0)
          vx-=0.2f;
		 else if(vx<0)
		   vx+=0.2f;
		
		 if(vz>0)
	       vz-=0.2f;
	     else if(vz<0)
	      vz+=0.2f;
         
        //�W�X��ɮ�
         if((currentX+0.05 < -tempFlag+(0)*UNIT_SIZE || currentX-0.05 > -tempFlag+(cols)*UNIT_SIZE)
       	      ||(currentZ+0.05 < -tempFlag/2+(-1)*UNIT_SIZE+1) || currentZ-0.05 > -tempFlag/2+(rows-1)*UNIT_SIZE+2)
       	      {
       		   state = 1;
       		   dx = vx;
       		   dz = vz;
       		if(soundFlag)
             activity.mpDrop.start();
       	      }
         //�b�a�ϤW��
         else
          for(int i=0;i<rows;i++)
           for(int j=0;j<cols;j++)
           {
        	if((currentX+vx*TIME_SPAN*6-0.1 >= -tempFlag+(j)*UNIT_SIZE 
               && currentX+vx*TIME_SPAN*6+0.1 < -tempFlag+((j+1))*UNIT_SIZE)
               &&(currentZ+vz*TIME_SPAN*6-0.1 >= -tempFlag/2+(i)*UNIT_SIZE 
               && currentZ+vz*TIME_SPAN*6+0.1 < -tempFlag/2+((i+1))*UNIT_SIZE))
              {
               if(MAP[i][j]==8 && !isSmall) //����
                {
            	   vx=-vx*2;
            	   vz=-vz*2;
            	   
         	     
                }
               else if(MAP[i][j]==11) //����
                {
            	 if(isBig)
            	  MAP[i][j] = 14;
            	 else
            	 {
            		 vx=-vx*2;
              	   vz=-vz*2;
            	 }
            	
            	}
               if(MAP[i][j]==99||MAP[i][j]==98) //����
               {
            	   vx=-vx*2;
            	   vz=-vz*2;
        	     
               }
               vTotal=(float)Math.sqrt(vx*vx+vz*vz);
           	
          	 //���`�t�פp���H�Ȯɻ{���y����F
          	 if(vTotal<MIN_SPEED)
          	 {
          	  vx=0;
          	  vz=0;
          	 }
              }
            if((currentX-0.05 > -tempFlag+(j)*UNIT_SIZE && currentX+0.05 < -tempFlag+((j+1))*UNIT_SIZE)
             &&(currentZ-0.05 > -tempFlag/2+(i)*UNIT_SIZE && currentZ+0.05 < -tempFlag/2+((i+1))*UNIT_SIZE))
            {
             //�S���a�O�ɱ���
             if(MAP[i][j]==0 || MAP[i][j] == 7)
   		     {
              state =1;
              dx = vx;
      		  dz = vz;
              if(soundFlag)
               activity.mpDrop.start();
             }
             //��L���a�O��
        	if(MAP[i][j]==2)
  		     {
              state =4;
             }
        	//�줤�~��
        	if(MAP[i][j]==3)
 		     {
        		startX=-tempFlag+(j+1)*UNIT_SIZE-0.5f; 
        		startZ=-tempFlag/2+(i+1)*UNIT_SIZE-0.5f;
        		MAP[i][j] = 16;
             }
        	if(MAP[i][j]==4) //�Y��ͩR
		     {
       		  left++;
              MAP[i][j]=1;
             }
        	if(MAP[i][j]==5) //�Y��ɶ�
		     {
        	  deadtimes+=50;
              MAP[i][j]=1;
             }
        	if(MAP[i][j]==6) //�Y��ɶ�
		     {
       	      new GraveltimeThread(i,j).start();
             }
        	if(MAP[i][j]==9) //�Y��}�G
		     {
        	  isBig = false;
        	  isSmall = true;
    	      ball = new BallForDraw(11.25f,SCALE*0.5f,ballTextureId);//�Y��}�G
    	      currentY=1f;
     	      MAP[i][j]=12;
             }
       	   if(MAP[i][j]==10) //�Y��״�
		     {
       	      isBig = true;
       	      isSmall = false;
       		  ball = new BallForDraw(11.25f,SCALE*1.5f,ballTextureId);
       	      currentY=1f;
        	  MAP[i][j]=13;
             }
       	   if(MAP[i][j]==15) //�Y��״�
	        {
       		 vx=vx*1.5f;
     	     vz=vz*1.5f;
            }
		    }
          }	
		}
		else if(state==1)//�U��
		{
			if(currentY <=-1f)
			{
			 state = 3;
			}
			
			timeDown++;
			float tempCurrentY=startY-0.5f*G*timeDown*timeDown/100;
			
			if(currentY>-0.2f)
			{
	    	 currentX=currentX+dx*TIME_SPAN;
	    	 currentZ=currentZ+dz*TIME_SPAN;
			}
			this.currentY=tempCurrentY;
		}
		else if(state==2)
		{//�Ӧ^�ϼu���q
			if(soundFlag)
             activity.mpDrop.pause();
			timeDown=timeDown+TIME_SPAN;
			float tempCurrentY=SCALE+HEIGHT+vy*timeDown-0.5f*G*timeDown*timeDown;	
			if(tempCurrentY<=SCALE+HEIGHT)
			{//�o�͸I��
				this.vy=(G*timeDown-vy)*ANERGY_LOST;
				timeDown=0;
			}
			else
			{
				this.currentY=tempCurrentY;
			}
		}
		currentX=currentX+vx*TIME_SPAN;
		currentZ=currentZ+vz*TIME_SPAN;
		 
		 
		 if(state == 1 && currentY>-0.2)
		 {
		  currentX=currentX-vx*TIME_SPAN;
		  currentZ=currentZ-vz*TIME_SPAN;
		 }
	}
	
	//�L���ɭ���
	public void closeSound()
	{
		if(soundFlag)
		{
		 activity.mpBack.stop();
		 activity.mpDrop.stop();
		 if(winFlag)
	    	{
			 activity.mpWin.start();
	    	}
		 if(loseFlag)
	    	{
			 activity.mpLose.start();
	    	}
		}
	}
	
	//���]
	public void restart()
	{
	 activity.mpDrop.pause();
	 
	 currentX=startX; 
	 currentY=startY;
	 currentZ=startZ;
	 
	 vx=0;
	 vz=0;
	 timeDown=0;
	 
	 isBig = false;
	 isSmall = false;
     ball = new BallForDraw(11.25f,SCALE,ballTextureId);
	 
     for(int i=0;i<rows;i++)
      for(int j=0;j<cols;j++)
       {
    	if(MAP[i][j]==7)
    	{
    	 MAP[i][j] = 6;
    	}
    	else if(MAP[i][j]==12)
    	{
       	 MAP[i][j] = 9;
       	}
    	else if(MAP[i][j]==13)
    	{
    	 MAP[i][j] = 10;
    	}
    	else if(MAP[i][j]==14)
    	{
       	 MAP[i][j] = 11;
       	}
       }
	}
	
	
}






