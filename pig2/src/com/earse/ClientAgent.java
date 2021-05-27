package com.earse;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.earse.GameConstant.*;

public class ClientAgent extends Thread
{
	Pig2Activity father;//Activity引用
	Socket sc;
	DataInputStream din;//輸入流
	DataOutputStream dout;//輸出流
	boolean flag=true;//是否結束客服端線程
	int num;//當前編號
	boolean perFlag=false;//是否為自己走棋標誌位,true為自己走,false為對方走
	
	public ClientAgent(Pig2Activity father,Socket sc,DataInputStream din,DataOutputStream dout)
	{
		this.father=father;//activity的引用
		this.sc=sc;//Socket引用
		this.din=din;//輸入流
		this.dout=dout;//輸出流
	}
	public void run()
	{
		while(flag)
		{
			try
			{
			 String msg=din.readUTF();//等待消息
			 if(msg.startsWith("<#ACCEPT#>"))//如果是成功的加入了遊戲,那麼進入等待界面
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
	//發送消息
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

