package com.earse;

import javax.microedition.khronos.opengles.GL10;
import static com.earse.GameConstant.*;

public class LevelNumber
{

	Panel[] numbers=new Panel[10];
	
	public LevelNumber(Numbers num)
	{
		numbers = num.numbers;
	}
	
	public void drawSelf(GL10 gl)
	{		
		String scoreStr=(level+1)+"";
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

