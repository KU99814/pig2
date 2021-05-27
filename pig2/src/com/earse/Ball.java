package com.earse;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Ball {

	private IntBuffer   mVertexBuffer;//頂點座標資料緩衝
	private IntBuffer   mNormalBuffer;//頂點法向量資料緩衝
    private FloatBuffer mTextureBuffer;//頂點紋理資料緩衝
    public float mAngleX;//沿x軸旋轉角度
    public float mAngleY;//沿y軸旋轉角度 
    public float mAngleZ;//沿z軸旋轉角度 
    int vCount=0;//頂點數量
    int textureId;//紋理ID
    
    public Ball(int scale,int textureId)
    {
    	this.textureId=textureId;
    	final int UNIT_SIZE=20000;
    	
        //實際頂點座標資料的初始化================begin============================
    	
    	ArrayList<Integer> alVertix=new ArrayList<Integer>();//存放頂點座標的ArrayList
    	final int angleSpan=18;//將球進行單位切分的角度
        for(int vAngle=-90;vAngle<=90;vAngle=vAngle+angleSpan)//垂直方向angleSpan度一份
        {
        	for(int hAngle=0;hAngle<360;hAngle=hAngle+angleSpan)//水準方向angleSpan度一份
        	{//縱向橫向各到一個角度後計算對應的此點在球面上的座標
        		double xozLength=scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle));
        		int x=(int)(xozLength*Math.cos(Math.toRadians(hAngle)));
        		int z=(int)(xozLength*Math.sin(Math.toRadians(hAngle)));
        		int y=(int)(scale*UNIT_SIZE*Math.sin(Math.toRadians(vAngle)));
        		//將計算出來的XYZ座標加入存放頂點座標的ArrayList
        		alVertix.add(x);alVertix.add(y);alVertix.add(z);
        	}
        } 	
        vCount=alVertix.size()/3;//頂點的數量為座標值數量的1/3，因為一個頂點有3個座標
    	
        //將alVertix中的座標值轉存到一個int陣列中
        int vertices[]=new int[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
        //實際頂點座標資料的初始化================end============================
        
               
        //三角形構造頂點、紋理、法向量數據初始化==========begin==========================
    	alVertix.clear();
        ArrayList<Float> alTexture=new ArrayList<Float>();//紋理  
        
        int row=(180/angleSpan)+1;//球面切分的行數
        int col=360/angleSpan;//球面切分的列數
        for(int i=0;i<row;i++)//對每一行迴圈
        {
        	if(i>0&&i<row-1)
        	{//中間行
        		for(int j=-1;j<col;j++)
				{//中間行的兩個相鄰點與下一行的對應點構成三角形
					int k=i*col+j;
					//第1個三角形頂點					
					alVertix.add(vertices[(k+col)*3]);
					alVertix.add(vertices[(k+col)*3+1]);
					alVertix.add(vertices[(k+col)*3+2]);					
					alTexture.add(0.0f);alTexture.add(0.0f);
					
					//第2個三角形頂點		
					alVertix.add(vertices[(k+1)*3]);
					alVertix.add(vertices[(k+1)*3+1]);
					alVertix.add(vertices[(k+1)*3+2]);					
					alTexture.add(1.0f);alTexture.add(1.0f);
					
					//第3個三角形頂點
					alVertix.add(vertices[k*3]);
					alVertix.add(vertices[k*3+1]);
					alVertix.add(vertices[k*3+2]);	
					alTexture.add(1.0f);alTexture.add(0.0f);
				}
        		for(int j=0;j<col+1;j++)
				{//中間行的兩個相鄰點與上一行的對應點構成三角形				
					int k=i*col+j;
					
					//第1個三角形頂點					
					alVertix.add(vertices[(k-col)*3]);
					alVertix.add(vertices[(k-col)*3+1]);
					alVertix.add(vertices[(k-col)*3+2]);					
					alTexture.add(1f);alTexture.add(1f);
					
					//第2個三角形頂點					
					alVertix.add(vertices[(k-1)*3]);
					alVertix.add(vertices[(k-1)*3+1]);
					alVertix.add(vertices[(k-1)*3+2]);					
					alTexture.add(0.0f);alTexture.add(0.0f);
					
					//第3個三角形頂點					
					alVertix.add(vertices[k*3]);
					alVertix.add(vertices[k*3+1]);
					alVertix.add(vertices[k*3+2]);					
					alTexture.add(0f);alTexture.add(1f);    
				}
        	}
        }
        
        vCount=alVertix.size()/3;//頂點的數量為座標值數量的1/3，因為一個頂點有3個座標
    	
        //將alVertix中的座標值轉存到一個int陣列中
        vertices=new int[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
        
        //建立繪製頂點資料緩衝
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//設置位元組順序
        mVertexBuffer = vbb.asIntBuffer();//轉換為int型緩衝
        mVertexBuffer.put(vertices);//向緩衝區中放入頂點座標資料
        mVertexBuffer.position(0);//設置緩衝區起始位置     
        
        //建立頂點法向量資料緩衝
        ByteBuffer nbb = ByteBuffer.allocateDirect(vertices.length*4);
        nbb.order(ByteOrder.nativeOrder());//設置位元組順序
        mNormalBuffer = vbb.asIntBuffer();//轉換為int型緩衝
        mNormalBuffer.put(vertices);//向緩衝區中放入頂點座標資料
        mNormalBuffer.position(0);//設置緩衝區起始位置
        
        //建立紋理座標緩衝
        float textureCoors[]=new float[alTexture.size()];//頂點紋理值陣列
        for(int i=0;i<alTexture.size();i++) 
        {
        	textureCoors[i]=alTexture.get(i);
        }
        
        ByteBuffer cbb = ByteBuffer.allocateDirect(textureCoors.length*4);
        cbb.order(ByteOrder.nativeOrder());//設置位元組順序
        mTextureBuffer = cbb.asFloatBuffer();//轉換為int型緩衝
        mTextureBuffer.put(textureCoors);//向緩衝區中放入頂點著色數據
        mTextureBuffer.position(0);//設置緩衝區起始位置
        
        //三角形構造頂點、紋理、法向量數據初始化==========end==============================
    }

    public void getId(int textureId)
    {
     this.textureId=textureId;
    }
    
    public void drawSelf(GL10 gl)
    {
    	gl.glRotatef(mAngleZ, 0, 0, 1);//沿Z軸旋轉
    	gl.glRotatef(mAngleX, 1, 0, 0);//沿X軸旋轉
        gl.glRotatef(mAngleY, 0, 1, 0);//沿Y軸旋轉
        
        //允許使用頂點陣列
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//為畫筆指定頂點座標資料
        gl.glVertexPointer
        (
        		3,				//每個頂點的座標數量為3  xyz 
        		GL10.GL_FIXED,	//頂點座標值的類型為 GL_FIXED
        		0, 				//連續頂點座標資料之間的間隔
        		mVertexBuffer	//頂點座標資料
        );
        
        //允許使用法向量陣列
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        //為畫筆指定頂點法向量數據
        gl.glNormalPointer(GL10.GL_FIXED, 0, mNormalBuffer);
		
        //開啟紋理
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //允許使用紋理ST座標緩衝
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //為畫筆指定紋理ST座標緩衝
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //設定當前紋理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
        
        //繪製圖形
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		0, 			 			//開始點編號
        		vCount					//頂點數量
        ); 
    }
}

