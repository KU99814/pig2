package com.earse;

import javax.microedition.khronos.opengles.GL10;
import static com.earse.GameConstant.*;

public class LeftNumber
{
	Panel[] numbers=new Panel[10];
	
	public LeftNumber(Numbers num)
	{
		numbers = num.numbers;
	}
	
	public void drawSelf(GL10 gl)
	{		
		String scoreStr=left+"";
		for(int i=0;i<scoreStr.length();i++)
		{//將得分中的每個數位字元繪製
			char c=scoreStr.charAt(i);
			gl.glPushMatrix();
			gl.glTranslatef(i*SCORE_NUMBER_SPAN_X, 0, 0);
			numbers[c-'0'].drawSelf(gl);
			gl.glPopMatrix();
		}
	}
}

