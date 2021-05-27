package com.earse;


import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ThreeDLoad  extends SurfaceView implements SurfaceHolder.Callback  //實作生命週期回呼介面
{
 public	static LoadedObjectVertexNormalTexture lovn_car_body;//車身 
 public	static LoadedObjectVertexNormalTexture tree;
 
 public ThreeDLoad(Pig2Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		lovn_car_body=
        LoadUtil.loadFromFileVertexOnly("test5.obj", Pig2Activity.rs);
		            
        tree=
    	LoadUtil.loadFromFileVertexOnly("tree.obj", Pig2Activity.rs);
	}

public void surfaceChanged(SurfaceHolder holder, int format, int width,
		int height) {
	// TODO Auto-generated method stub
	
}
public void surfaceCreated(SurfaceHolder holder) {
	// TODO Auto-generated method stub
	
}
public void surfaceDestroyed(SurfaceHolder holder) {
	// TODO Auto-generated method stub
	
}
 
 
}
