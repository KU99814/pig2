package com.earse;



public class LoadThreeDModel {
	
	public static LoadedObjectVertexNormalTexture tree1;//«Å§i¾ð
	public static LoadedObjectVertexNormalTexture tree2;//«Å§i¾ð
	public static LoadedObjectVertexNormalTexture sugar1;//«Å§i¿}
	public static LoadedObjectVertexNormalTexture sugar2;//«Å§i¿}
	public static LoadedObjectVertexNormalTexture meat1;//«Å§i¦×
	public static LoadedObjectVertexNormalTexture meat2;//«Å§i¦×
	public static LoadedObjectVertexNormalTexture stair;//«Å§i¦×
	public static LoadedObjectVertexNormalTexture fance1;//«Å§i¦×
	public static LoadedObjectVertexNormalTexture fance2;//«Å§i¦×
	
	public static int tree1Id;
	public static int tree2Id;
	public static int tree3Id;
	public static int tree4Id;
	
	public static int sugar1Id;
	public static int sugar2Id;
	
	public static int meat1Id;
	public static int meat2Id;
	
	public static int fanceId;
	
	
	public static int stairId;
	
	
	
	public static boolean isLoad = false;
	
	public LoadThreeDModel()
	{}
	
	public static void Loading()
	{
		tree1=LoadUtil.loadFromFileVertexOnly("tree1.obj", Pig2Activity.rs);
		tree2=LoadUtil.loadFromFileVertexOnly("tree2.obj", Pig2Activity.rs);
					    
	    sugar1=LoadUtil.loadFromFileVertexOnly("sugar1.obj", Pig2Activity.rs);
	    sugar2=LoadUtil.loadFromFileVertexOnly("sugar2.obj", Pig2Activity.rs);
			    
	    meat1=LoadUtil.loadFromFileVertexOnly("meat1.obj", Pig2Activity.rs);
	    meat2=LoadUtil.loadFromFileVertexOnly("meat2.obj", Pig2Activity.rs);
	    
	    stair=LoadUtil.loadFromFileVertexOnly("stair1.obj", Pig2Activity.rs);
	    
	    fance1=LoadUtil.loadFromFileVertexOnly("fance1.obj", Pig2Activity.rs);
	    fance2=LoadUtil.loadFromFileVertexOnly("fance2.obj", Pig2Activity.rs);
	    
	    
	    isLoad = true;
	}
	
	
}
