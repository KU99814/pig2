package com.earse;

import static com.earse.GameConstant.*;

public class Numbers
{

	Panel[] numbers=new Panel[10];
	
	public Numbers(int texId)
	{
		//�ͦ�0-9�Q�ӼƦr�����z�x��
		for(int i=0;i<10;i++)
		{
			numbers[i]=new Panel
            (
            	SCORE_NUMBER_SPAN_X,
            	SCORE_NUMBER_SPAN_Y,
            	 texId,
            	 new float[]
		             {
		           	  0.1f*i,0, 0.1f*i,1, 0.1f*(i+1),0,
		           	  0.1f*(i+1),0, 0.1f*i,1,  0.1f*(i+1),1
		             }
             ); 
		}
	}
}

