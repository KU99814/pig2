package com.earse;

import javax.microedition.khronos.opengles.GL10;
import static com.earse.GameConstant.*;

public class Deadtime
{

	Panel[] numbers=new Panel[10];
	
	public Deadtime(Numbers num)
	{
		//�ͦ�0-9�Q�ӼƦr�����z�x��
		numbers = num.numbers;
	}
	
	public void drawSelf(GL10 gl)
	{		
		String scoreStr=deadtimes+"";
		for(int i=0;i<scoreStr.length();i++)
		{//�N�o�������C�ӼƦ�r��ø�s
			char c=scoreStr.charAt(i);
			gl.glPushMatrix();
			gl.glTranslatef(i*SCORE_NUMBER_SPAN_X, 0, 0);
			numbers[c-'0'].drawSelf(gl);
			gl.glPopMatrix();
		}
	}
}

