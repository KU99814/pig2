package com.earse;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Panel
{
	private FloatBuffer myVertex;
	private FloatBuffer myTexture;
	
	float width;
	float height;
	
	int vcount;
	int textureid;
	
	public Panel(float width,float height,int textureid,float[] textures)
	{
		this.width=width;
		this.height=height;
		this.textureid=textureid;
		
		vcount=6;
		float[] vertexs=new float[]
		{
			-width/2,height/2,0,
			-width/2,-height/2,0,
			width/2,height/2,0,
			
			width/2,height/2,0,
			-width/2,-height/2,0,
			width/2,-height/2,0
		};
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexs.length*4);
        vbb.order(ByteOrder.nativeOrder());//設置位元組順序
        myVertex = vbb.asFloatBuffer();//轉換為int型緩衝
        myVertex.put(vertexs);//向緩衝區中放入頂點座標資料
        myVertex.position(0);//設置緩衝區起始位置

        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());//設置位元組順序
        myTexture = tbb.asFloatBuffer();//轉換為int型緩衝
        myTexture.put(textures);//向緩衝區中放入頂點座標資料
        myTexture.position(0);//設置緩衝區起始位置
	
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertex);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureid);
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vcount);
	}
}

