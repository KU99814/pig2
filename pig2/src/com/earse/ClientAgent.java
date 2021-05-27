package com.earse;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.earse.GameConstant.*;

public class ClientAgent extends Thread
{
	Pig2Activity father;//Activity�ޥ�
	Socket sc;
	DataInputStream din;//��J�y
	DataOutputStream dout;//��X�y
	boolean flag=true;//�O�_�����ȪA�ݽu�{
	int num;//��e�s��
	boolean perFlag=false;//�O�_���ۤv���Ѽлx��,true���ۤv��,false����訫
	
	public ClientAgent(Pig2Activity father,Socket sc,DataInputStream din,DataOutputStream dout)
	{
		this.father=father;//activity���ޥ�
		this.sc=sc;//Socket�ޥ�
		this.din=din;//��J�y
		this.dout=dout;//��X�y
	}
	public void run()
	{
		while(flag)
		{
			try
			{
			 String msg=din.readUTF();//���ݮ���
			 if(msg.startsWith("<#ACCEPT#>"))//�p�G�O���\���[�J�F�C��,����i�J���ݬɭ�
				{
				 father.toAnotherView(ENTER_MENU);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	//�o�e����
	public void sendMessage(String msg)
	{
		try 
		{
			dout.writeUTF(msg);
		} catch (IOException e) 
		{			
			e.printStackTrace();
		}
	}
}

