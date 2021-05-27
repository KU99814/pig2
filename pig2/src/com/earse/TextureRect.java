package com.earse;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import static com.earse.GameConstant.*;
//���z�x��
public class TextureRect {
	private FloatBuffer   mVertexBuffer;//���I�y�и�ƽw��
    private FloatBuffer mTextureBuffer;//���I���z��ƽw��
    int vCount=0;
    int texId;
    float yAngle;
    float xOffset;
    public TextureRect(float width,float height,int texId,float[] texST)
    {
    	this.texId=texId;
    	//���I�y�и�ƪ���l��================begin============================
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
		
        //�إ߳��I�y�и�ƽw��
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�]�m�줸�ն���
        mVertexBuffer = vbb.asFloatBuffer();//�ഫ��Float���w��
        mVertexBuffer.put(vertices);//�V�w�İϤ���J���I�y�и��
        mVertexBuffer.position(0);//�]�m�w�İϰ_�l��m
        //�S�O���ܡG�ѩ󤣦P���O�줸�ն��Ǥ��P��Ƴ椸���O�줸�ժ��@�w�n�g�LByteBuffer
        //�ഫ�A����O�n�z�LByteOrder�]�mnativeOrder()�A�_�h���i��|�X���D
        //���I�y�и�ƪ���l��================end============================
        ByteBuffer tbb = ByteBuffer.allocateDirect(texST.length*4);
        tbb.order(ByteOrder.nativeOrder());//�]�m�줸�ն���
        mTextureBuffer = tbb.asFloatBuffer();//�ഫ��int���w��
        mTextureBuffer.put(texST);//�V�w�İϤ���J���I�ۦ�ƾ�
        mTextureBuffer.position(0);//�]�m�w�İϰ_�l��m         
        
    }

    public void drawSelf(GL10 gl)
    {        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//�ҥγ��I�y�а}�C
        gl.glRotatef(yAngle, 0, 1, 0);
        gl.glTranslatef(xOffset, 0, 0);
		//���e�����w���I�y�и��
        gl.glVertexPointer
        (
        		3,				//�C�ӳ��I���y�мƶq��3  xyz 
        		GL10.GL_FLOAT,	//���I�y�ЭȪ������� GL_FIXED
        		0, 				//�s���I�y�и�Ƥ��������j
        		mVertexBuffer	//���I�y�и��
        );
        
        //�}�ү��z
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //���\�ϥί��zST�y�нw��
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //���e�����w���zST�y�нw��
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //�]�w��e���z
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
        //ø�s�ϧ�
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//�H�T���Τ覡��R
        		0, 			 			//�}�l�I�s��
        		vCount					//���I���ƶq
        );
    }
}


