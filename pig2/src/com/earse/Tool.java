package com.earse;


import javax.microedition.khronos.opengles.GL10;

import static com.earse.GameConstant.*;
import static com.earse.LoadThreeDModel.*;

public class Tool {

	int[] allToolId;
	Ball toolBall;
	
	public Tool(int[] allToolId)
	{
		this.allToolId = allToolId;
		toolBall = new Ball(1,allToolId[0]);
	}
	public void drawSelf(GL10 gl)
	{
		int rows=MAP.length;
		int cols=MAP[0].length;
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
			 if(MAP[i][j]==2)
			  {		
			   gl.glPushMatrix();
		       gl.glTranslatef(-tempFlag+j*UNIT_SIZE+0.5f,
			                   0.5f,
							   -tempFlag/2+i*UNIT_SIZE+0.5f);
			   stair.drawSelf(gl,stairId);
		       gl.glPopMatrix();
	          }
			 else if(MAP[i][j]==4)
			  {
					
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE+0.5f,
							0.5f,
							-tempFlag/2+i*UNIT_SIZE+0.5f);
					toolBall.getId(allToolId[0]);
					toolBall.drawSelf(gl);
					gl.glPopMatrix();
				}
				
				else if(MAP[i][j]==5)
				{
					
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE+0.5f,
							0.5f,
							-tempFlag/2+i*UNIT_SIZE+0.5f);
					toolBall.getId(allToolId[1]);
					toolBall.drawSelf(gl);
					gl.glPopMatrix();
				}
				else if(MAP[i][j]==8) //攫れ家舱
				{
					 
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE+0.5f,
							1.2f,
							-tempFlag/2+i*UNIT_SIZE+0.5f);
					
					tree1.drawSelf(gl,tree1Id);
					gl.glPopMatrix();
					
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE+0.5f,
							0.1f,
							-tempFlag/2+i*UNIT_SIZE+0.5f);
					tree2.drawSelf(gl,tree2Id);
					gl.glPopMatrix();
				}
				else if(MAP[i][j]==9) //縸狦家舱
				{
					
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE+0.5f,
							0.5f,
							-tempFlag/2+i*UNIT_SIZE+0.5f);
					
					sugar1.drawSelf(gl,sugar1Id);
					gl.glPopMatrix();
					
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE+0.5f,
							0.5f,
							-tempFlag/2+i*UNIT_SIZE+0.5f);
					sugar2.drawSelf(gl,sugar2Id);
					gl.glPopMatrix();
				}
				else if(MAP[i][j]==10) //ψ次家舱
				{
					
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE+0.5f,
							0.5f,
							-tempFlag/2+i*UNIT_SIZE+0.5f);
					
					meat1.drawSelf(gl,meat1Id);
					gl.glPopMatrix();
					
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE+0.5f,
							0.5f,
							-tempFlag/2+i*UNIT_SIZE+0.5f);
					meat2.drawSelf(gl,meat2Id);
					gl.glPopMatrix();
				}
				else if(MAP[i][j]==11) //攫家舱
				{
					
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE+0.5f,
							1.2f,
							-tempFlag/2+i*UNIT_SIZE+0.5f);
					
					tree1.drawSelf(gl,tree3Id);
					gl.glPopMatrix();
					
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE+0.5f,
							0.1f,
							-tempFlag/2+i*UNIT_SIZE+0.5f);
					tree2.drawSelf(gl,tree4Id);
					gl.glPopMatrix();
				}
				else if(MAP[i][j]==99)
				  {
						
						gl.glPushMatrix();
						gl.glTranslatef(-tempFlag+j*UNIT_SIZE+0.5f,
								0.5f,
								-tempFlag/2+i*UNIT_SIZE+0.5f);
						
						fance1.drawSelf(gl,fanceId);
						gl.glPopMatrix();
					}
				else if(MAP[i][j]==98)
				  {
						
						gl.glPushMatrix();
						gl.glTranslatef(-tempFlag+j*UNIT_SIZE+0.5f,
								0.5f,
								-tempFlag/2+i*UNIT_SIZE+0.5f);
						
						fance2.drawSelf(gl,fanceId);
						gl.glPopMatrix();
					}
			}
		}
	}
}


