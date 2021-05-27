package com.earse;


import javax.microedition.khronos.opengles.GL10;

import static com.earse.GameConstant.*;

public class FloorGroup {

	Floor floor;

	int scale;
	int[] allFloorId;
	
	public FloorGroup(int scale,int[] allFloorId)
	{
		this.scale = scale;
		this.allFloorId = allFloorId;
		floor = new Floor(scale,allFloorId[0]);
	}
	public void drawSelf(GL10 gl)
	{
		int rows=MAP.length;
		int cols=MAP[0].length;
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				//一般地板
				if(MAP[i][j]==1||MAP[i][j]==4||MAP[i][j]==5||MAP[i][j]==10
			    ||MAP[i][j] == 9||MAP[i][j]==12||MAP[i][j]==13||MAP[i][j]==14)
				{
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE,
							0,
							-tempFlag/2+i*UNIT_SIZE);
					floor.getId(allFloorId[0]);
					floor.drawSelf(gl);
					gl.glPopMatrix();
				}
				//過關地板
				else if(MAP[i][j]==2)
				{
					
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE,
							0,
							-tempFlag/2+i*UNIT_SIZE);
					floor.getId(allFloorId[1]);
					floor.drawSelf(gl);
					gl.glPopMatrix();
				}
				//中繼點
				else if(MAP[i][j]==3)
				{
					
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE,
							0,
							-tempFlag/2+i*UNIT_SIZE);
					floor.getId(allFloorId[2]);
					floor.drawSelf(gl);
					gl.glPopMatrix();
				}
				else if(MAP[i][j]==6)
				{
					
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE,
							0,
							-tempFlag/2+i*UNIT_SIZE);
					floor.getId(allFloorId[3]);
					floor.drawSelf(gl);
					gl.glPopMatrix();
				}
				else if(MAP[i][j]==12||MAP[i][j]==8||MAP[i][j]==11||MAP[i][j]==99||MAP[i][j]==98) //泥土地板
				{
				 gl.glPushMatrix();
				 gl.glTranslatef(-tempFlag+j*UNIT_SIZE,
							     0,
							     -tempFlag/2+i*UNIT_SIZE);
				 floor.getId(allFloorId[4]);
				 floor.drawSelf(gl);
				 gl.glPopMatrix();
				}
				else if(MAP[i][j]==15)
				{
				 gl.glPushMatrix();
				 gl.glTranslatef(-tempFlag+j*UNIT_SIZE,
							0,
							-tempFlag/2+i*UNIT_SIZE);
				 floor.getId(allFloorId[5]);
				 floor.drawSelf(gl);
				 gl.glPopMatrix();
				}
				else if(MAP[i][j]==16)
				{
				 gl.glPushMatrix();
				 gl.glTranslatef(-tempFlag+j*UNIT_SIZE,
							0,
							-tempFlag/2+i*UNIT_SIZE);
				 floor.getId(allFloorId[6]);
				 floor.drawSelf(gl);
				 gl.glPopMatrix();
				}
			}
		}
	}
}


