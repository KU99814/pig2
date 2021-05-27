package com.earse;

public class RotateUtil
{
	//angle為弧度 gVector  為重力向量[x,y,z,1]
	//返回值為旋轉後的向量
	public static double[] pitchRotate(double angle,double[] gVector)
	{
		double[][] matrix=//繞x軸旋轉變換矩陣
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
	
	//angle為弧度 gVector  為重力向量[x,y,z,1]
	//返回值為旋轉後的向量
	public static double[] rollRotate(double angle,double[] gVector)
	{
		double[][] matrix=//繞y軸旋轉變換矩陣
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
	
	//angle為弧度 gVector  為重力向量[x,y,z,1]
	//返回值為旋轉後的向量
	public static double[] yawRotate(double angle,double[] gVector)
	{
		double[][] matrix=//繞z軸旋轉變換矩陣
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
		double yawAngle=-Math.toRadians(values[0]);//獲取Yaw軸旋轉角度弧度
		double pitchAngle=-Math.toRadians(values[1]);//獲取Pitch軸旋轉角度弧度
		double rollAngle=-Math.toRadians(values[2]);//獲取Roll軸旋轉角度弧度

		double[] gVector={0,0,-100,1};
		
		//yaw軸恢復
		gVector=RotateUtil.yawRotate(yawAngle,gVector);
		
		//pitch軸恢復
		gVector=RotateUtil.pitchRotate(pitchAngle,gVector);	
		
		//roll軸恢復
		gVector=RotateUtil.rollRotate(rollAngle,gVector);
		
		double mapX=gVector[0];
		double mapY=gVector[1];		
		
		int[] result={(int)mapX,(int)mapY};
		return result;
	}	
}

