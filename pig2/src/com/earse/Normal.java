package com.earse;

import java.util.Set;
public class Normal 
{
   public static final float DIFF=0.0000001f;//�P�_��Ӫk�V�q�O�_�ۦP���H��
   //�k�V�q�bXYZ�b�W�����q
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
	   {//�Y��Ӫk�V�qXYZ�T�Ӥ��q���t���p����w���H�ȫh�{���o��Ӫk�V�q�۵�
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
   //�D�k�V�q�����Ȫ��u���k
   public static float[] getAverage(Set<Normal> sn)
   {
	   //�s��k�V�q�M���}�C
	   float[] result=new float[3];
	   for(Normal n:sn)
	   {
		   result[0]+=n.nx;
		   result[1]+=n.ny;
		   result[2]+=n.nz;
	   }	   
	   //�W���
	   return LoadUtil.vectorNormal(result);
   }
}

