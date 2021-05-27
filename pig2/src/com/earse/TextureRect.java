package com.earse;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import static com.earse.GameConstant.*;
//紋理矩形
public class TextureRect {
	private FloatBuffer   mVertexBuffer;//頂點座標資料緩衝
    private FloatBuffer mTextureBuffer;//頂點紋理資料緩衝
    int vCount=0;
    int texId;
    float yAngle;
    float xOffset;
    public TextureRect(float width,float height,int texId,float[] texST)
    {
    	this.texId=texId;
    	//頂點座標資料的初始化================begin============================
        vCount=6;
        float vertices[]=new float[]
        {
        	width * UNIT_SIZE,height * UNIT_SIZE,0,
        	-width*UNIT_SIZE,height*UNIT_SIZE,0,
        	-width*UNIT_SIZE,-height*UNIT_SIZE,0,
        	     
        	-width*UNIT_SIZE,-height*UNIT_SIZE,0,
        	width*UNIT_SIZE,-height*UNIT_SIZE,0,  
        	width*UNIT_SIZE,height*UNIT_SIZE,0, 
        };
		
        //建立頂點座標資料緩衝
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//設置位元組順序
        mVertexBuffer = vbb.asFloatBuffer();//轉換為Float型緩衝
        mVertexBuffer.put(vertices);//向緩衝區中放入頂點座標資料
        mVertexBuffer.position(0);//設置緩衝區起始位置
        //特別提示：由於不同平臺位元組順序不同資料單元不是位元組的一定要經過ByteBuffer
        //轉換，關鍵是要透過ByteOrder設置nativeOrder()，否則有可能會出問題
        //頂點座標資料的初始化================end============================
        ByteBuffer tbb = ByteBuffer.allocateDirect(texST.length*4);
        tbb.order(ByteOrder.nativeOrder());//設置位元組順序
        mTextureBuffer = tbb.asFloatBuffer();//轉換為int型緩衝
        mTextureBuffer.put(texST);//向緩衝區中放入頂點著色數據
        mTextureBuffer.position(0);//設置緩衝區起始位置         
        
    }

    public void drawSelf(GL10 gl)
    {        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//啟用頂點座標陣列
        gl.glRotatef(yAngle, 0, 1, 0);
        gl.glTranslatef(xOffset, 0, 0);
		//為畫筆指定頂點座標資料
        gl.glVertexPointer
        (
        		3,				//每個頂點的座標數量為3  xyz 
        		GL10.GL_FLOAT,	//頂點座標值的類型為 GL_FIXED
        		0, 				//連續頂點座標資料之間的間隔
        		mVertexBuffer	//頂點座標資料
        );
        
        //開啟紋理
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //允許使用紋理ST座標緩衝
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //為畫筆指定紋理ST座標緩衝
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //設定當前紋理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
        //繪製圖形
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		0, 			 			//開始點編號
        		vCount					//頂點的數量
        );
    }
}


