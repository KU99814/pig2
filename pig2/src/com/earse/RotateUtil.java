package com.earse;

public class RotateUtil
{
	//angle������ gVector  �����O�V�q[x,y,z,1]
	//��^�Ȭ�����᪺�V�q
	public static double[] pitchRotate(double angle,double[] gVector)
	{
		double[][] matrix=//¶x�b�����ܴ��x�}
		{
		   {1,0,0,0},
		   {0,Math.cos(angle),Math.sin(angle),0},		   
		   {0,-Math.sin(angle),Math.cos(angle),0},		  
		   {0,0,0,1}	
		};
		
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		
		return gVector;
	}
	
	//angle������ gVector  �����O�V�q[x,y,z,1]
	//��^�Ȭ�����᪺�V�q
	public static double[] rollRotate(double angle,double[] gVector)
	{
		double[][] matrix=//¶y�b�����ܴ��x�}
		{
		   {Math.cos(angle),0,-Math.sin(angle),0},
		   {0,1,0,0},
		   {Math.sin(angle),0,Math.cos(angle),0},
		   {0,0,0,1}	
		};
		
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		
		return gVector;
	}		
	
	//angle������ gVector  �����O�V�q[x,y,z,1]
	//��^�Ȭ�����᪺�V�q
	public static double[] yawRotate(double angle,double[] gVector)
	{
		double[][] matrix=//¶z�b�����ܴ��x�}
		{
		   {Math.cos(angle),Math.sin(angle),0,0},		   
		   {-Math.sin(angle),Math.cos(angle),0,0},
		   {0,0,1,0},
		   {0,0,0,1}	
		};
		
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		
		return gVector;
	}
	
	
	public static int[] getDirectionDot(double[] values)
	{
		double yawAngle=-Math.toRadians(values[0]);//���Yaw�b���ਤ�ש���
		double pitchAngle=-Math.toRadians(values[1]);//���Pitch�b���ਤ�ש���
		double rollAngle=-Math.toRadians(values[2]);//���Roll�b���ਤ�ש���

		double[] gVector={0,0,-100,1};
		
		//yaw�b��_
		gVector=RotateUtil.yawRotate(yawAngle,gVector);
		
		//pitch�b��_
		gVector=RotateUtil.pitchRotate(pitchAngle,gVector);	
		
		//roll�b��_
		gVector=RotateUtil.rollRotate(rollAngle,gVector);
		
		double mapX=gVector[0];
		double mapY=gVector[1];		
		
		int[] result={(int)mapX,(int)mapY};
		return result;
	}	
}

