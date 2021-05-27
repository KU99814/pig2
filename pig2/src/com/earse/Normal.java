package com.earse;

import java.util.Set;
public class Normal 
{
   public static final float DIFF=0.0000001f;//判斷兩個法向量是否相同的閾值
   //法向量在XYZ軸上的分量
   float nx;
   float ny;
   float nz;
   
   public Normal(float nx,float ny,float nz)
   {
	   this.nx=nx;
	   this.ny=ny;
	   this.nz=nz;
   }
   
   @Override 
   public boolean equals(Object o)
   {
	   if(o instanceof  Normal)
	   {//若兩個法向量XYZ三個分量的差都小於指定的閾值則認為這兩個法向量相等
		   Normal tn=(Normal)o;
		   if(Math.abs(nx-tn.nx)<DIFF&&
			  Math.abs(ny-tn.ny)<DIFF&&
			  Math.abs(ny-tn.ny)<DIFF
             )
		   {
			   return true;
		   }
		   else
		   {
			   return false;
		   }
	   }
	   else
	   {
		   return false;
	   }
   }
   @Override
   public int hashCode()
   {
	   return 1;
   }   
   //求法向量平均值的工具方法
   public static float[] getAverage(Set<Normal> sn)
   {
	   //存放法向量和的陣列
	   float[] result=new float[3];
	   for(Normal n:sn)
	   {
		   result[0]+=n.nx;
		   result[1]+=n.ny;
		   result[2]+=n.nz;
	   }	   
	   //規格化
	   return LoadUtil.vectorNormal(result);
   }
}

