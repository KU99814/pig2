package com.earse;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Ball {

	private IntBuffer   mVertexBuffer;//���I�y�и�ƽw��
	private IntBuffer   mNormalBuffer;//���I�k�V�q��ƽw��
    private FloatBuffer mTextureBuffer;//���I���z��ƽw��
    public float mAngleX;//�ux�b���ਤ��
    public float mAngleY;//�uy�b���ਤ�� 
    public float mAngleZ;//�uz�b���ਤ�� 
    int vCount=0;//���I�ƶq
    int textureId;//���zID
    
    public Ball(int scale,int textureId)
    {
    	this.textureId=textureId;
    	final int UNIT_SIZE=20000;
    	
        //��ڳ��I�y�и�ƪ���l��================begin============================
    	
    	ArrayList<Integer> alVertix=new ArrayList<Integer>();//�s���I�y�Ъ�ArrayList
    	final int angleSpan=18;//�N�y�i�������������
        for(int vAngle=-90;vAngle<=90;vAngle=vAngle+angleSpan)//������VangleSpan�פ@��
        {
        	for(int hAngle=0;hAngle<360;hAngle=hAngle+angleSpan)//���Ǥ�VangleSpan�פ@��
        	{//�a�V��V�U��@�Ө��׫�p����������I�b�y���W���y��
        		double xozLength=scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle));
        		int x=(int)(xozLength*Math.cos(Math.toRadians(hAngle)));
        		int z=(int)(xozLength*Math.sin(Math.toRadians(hAngle)));
        		int y=(int)(scale*UNIT_SIZE*Math.sin(Math.toRadians(vAngle)));
        		//�N�p��X�Ӫ�XYZ�y�Х[�J�s���I�y�Ъ�ArrayList
        		alVertix.add(x);alVertix.add(y);alVertix.add(z);
        	}
        } 	
        vCount=alVertix.size()/3;//���I���ƶq���y�Эȼƶq��1/3�A�]���@�ӳ��I��3�Ӯy��
    	
        //�NalVertix�����y�Э���s��@��int�}�C��
        int vertices[]=new int[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
        //��ڳ��I�y�и�ƪ���l��================end============================
        
               
        //�T���κc�y���I�B���z�B�k�V�q�ƾڪ�l��==========begin==========================
    	alVertix.clear();
        ArrayList<Float> alTexture=new ArrayList<Float>();//���z  
        
        int row=(180/angleSpan)+1;//�y�����������
        int col=360/angleSpan;//�y���������C��
        for(int i=0;i<row;i++)//��C�@��j��
        {
        	if(i>0&&i<row-1)
        	{//������
        		for(int j=-1;j<col;j++)
				{//�����檺��Ӭ۾F�I�P�U�@�檺�����I�c���T����
					int k=i*col+j;
					//��1�ӤT���γ��I					
					alVertix.add(vertices[(k+col)*3]);
					alVertix.add(vertices[(k+col)*3+1]);
					alVertix.add(vertices[(k+col)*3+2]);					
					alTexture.add(0.0f);alTexture.add(0.0f);
					
					//��2�ӤT���γ��I		
					alVertix.add(vertices[(k+1)*3]);
					alVertix.add(vertices[(k+1)*3+1]);
					alVertix.add(vertices[(k+1)*3+2]);					
					alTexture.add(1.0f);alTexture.add(1.0f);
					
					//��3�ӤT���γ��I
					alVertix.add(vertices[k*3]);
					alVertix.add(vertices[k*3+1]);
					alVertix.add(vertices[k*3+2]);	
					alTexture.add(1.0f);alTexture.add(0.0f);
				}
        		for(int j=0;j<col+1;j++)
				{//�����檺��Ӭ۾F�I�P�W�@�檺�����I�c���T����				
					int k=i*col+j;
					
					//��1�ӤT���γ��I					
					alVertix.add(vertices[(k-col)*3]);
					alVertix.add(vertices[(k-col)*3+1]);
					alVertix.add(vertices[(k-col)*3+2]);					
					alTexture.add(1f);alTexture.add(1f);
					
					//��2�ӤT���γ��I					
					alVertix.add(vertices[(k-1)*3]);
					alVertix.add(vertices[(k-1)*3+1]);
					alVertix.add(vertices[(k-1)*3+2]);					
					alTexture.add(0.0f);alTexture.add(0.0f);
					
					//��3�ӤT���γ��I					
					alVertix.add(vertices[k*3]);
					alVertix.add(vertices[k*3+1]);
					alVertix.add(vertices[k*3+2]);					
					alTexture.add(0f);alTexture.add(1f);    
				}
        	}
        }
        
        vCount=alVertix.size()/3;//���I���ƶq���y�Эȼƶq��1/3�A�]���@�ӳ��I��3�Ӯy��
    	
        //�NalVertix�����y�Э���s��@��int�}�C��
        vertices=new int[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
        
        //�إ�ø�s���I��ƽw��
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�]�m�줸�ն���
        mVertexBuffer = vbb.asIntBuffer();//�ഫ��int���w��
        mVertexBuffer.put(vertices);//�V�w�İϤ���J���I�y�и��
        mVertexBuffer.position(0);//�]�m�w�İϰ_�l��m     
        
        //�إ߳��I�k�V�q��ƽw��
        ByteBuffer nbb = ByteBuffer.allocateDirect(vertices.length*4);
        nbb.order(ByteOrder.nativeOrder());//�]�m�줸�ն���
        mNormalBuffer = vbb.asIntBuffer();//�ഫ��int���w��
        mNormalBuffer.put(vertices);//�V�w�İϤ���J���I�y�и��
        mNormalBuffer.position(0);//�]�m�w�İϰ_�l��m
        
        //�إ߯��z�y�нw��
        float textureCoors[]=new float[alTexture.size()];//���I���z�Ȱ}�C
        for(int i=0;i<alTexture.size();i++) 
        {
        	textureCoors[i]=alTexture.get(i);
        }
        
        ByteBuffer cbb = ByteBuffer.allocateDirect(textureCoors.length*4);
        cbb.order(ByteOrder.nativeOrder());//�]�m�줸�ն���
        mTextureBuffer = cbb.asFloatBuffer();//�ഫ��int���w��
        mTextureBuffer.put(textureCoors);//�V�w�İϤ���J���I�ۦ�ƾ�
        mTextureBuffer.position(0);//�]�m�w�İϰ_�l��m
        
        //�T���κc�y���I�B���z�B�k�V�q�ƾڪ�l��==========end==============================
    }

    public void getId(int textureId)
    {
     this.textureId=textureId;
    }
    
    public void drawSelf(GL10 gl)
    {
    	gl.glRotatef(mAngleZ, 0, 0, 1);//�uZ�b����
    	gl.glRotatef(mAngleX, 1, 0, 0);//�uX�b����
        gl.glRotatef(mAngleY, 0, 1, 0);//�uY�b����
        
        //���\�ϥγ��I�}�C
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//���e�����w���I�y�и��
        gl.glVertexPointer
        (
        		3,				//�C�ӳ��I���y�мƶq��3  xyz 
        		GL10.GL_FIXED,	//���I�y�ЭȪ������� GL_FIXED
        		0, 				//�s���I�y�и�Ƥ��������j
        		mVertexBuffer	//���I�y�и��
        );
        
        //���\�ϥΪk�V�q�}�C
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        //���e�����w���I�k�V�q�ƾ�
        gl.glNormalPointer(GL10.GL_FIXED, 0, mNormalBuffer);
		
        //�}�ү��z
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //���\�ϥί��zST�y�нw��
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //���e�����w���zST�y�нw��
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //�]�w��e���z
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
        
        //ø�s�ϧ�
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//�H�T���Τ覡��R
        		0, 			 			//�}�l�I�s��
        		vCount					//���I�ƶq
        ); 
    }
}

