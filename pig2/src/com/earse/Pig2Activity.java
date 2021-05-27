package com.earse;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


import static com.earse.GameConstant.*;
import static com.earse.LoadThreeDModel.*;

enum WhichView {WELCOME_VIEW,MAIN_MENU,IP_VIEW,GL_VIEW,LOAD_VIEW,
	   WAIT_VIEW,WIN_VIEW,EXIT_VIEW,FULL_VIEW,ABOUT_VIEW,HELP_VIEW}

public class Pig2Activity extends Activity{
	Menuview mainmenu;//主畫面
	ChooseTable chooseTable;
	MySurfaceView v1; //遊戲畫面
	Setting setting; //設定畫面
	RecordTable recordTable;
	Makemap makemap;//地圖製作
	CreateMapview cmv;
	ViewLoading vld;
	Pass pass;
	
	Handler hd;//訊息處理器
	YouWin winView; //勝利/失敗畫面

	static Resources rs;

	SensorManager mySensorManager;
	
	MediaPlayer mpBack;//播放背景音樂
	MediaPlayer mpWin; //勝利音樂
	MediaPlayer mpLose; //失敗音樂
	MediaPlayer mpDrop; //掉落音樂
	
	//DeadtimeThread  deadtimeThread;
	
	ClientAgent ca;//客戶端代理線程
	WhichView curr;//當前枚舉值
	//ScoreNumberThread scoreNumberThread;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	rs=this.getResources();
    	
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);		//設定不顯示標題
    	//設置為直螢幕
    	this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	getWindow().setFlags(								//設定為全螢幕模式
    		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
    		WindowManager.LayoutParams.FLAG_FULLSCREEN
    	);
    	
    	mpBack=MediaPlayer.create(this,R.raw.top);
    	mpWin=MediaPlayer.create(this,R.raw.winsound);
    	mpLose=MediaPlayer.create(this,R.raw.lose);	
    	mpDrop=MediaPlayer.create(this,R.raw.drop);	
		    	
    	mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    	
    	//切換
    	hd=new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				switch(msg.what)
				{
				//主選單
				case ENTER_MENU:
					threadFlag = true;
					status = -1;
					level=0;
					stopSound();
					initSound();
					mainmenu=new Menuview(Pig2Activity.this);
				    mainmenu.requestFocus();
				    mainmenu.setFocusableInTouchMode(true);
					setContentView(mainmenu);
					left = 3;
					toInit();
					break;
				case ENTER_CHOOSE:
					chooseTable=new ChooseTable(Pig2Activity.this);
					chooseTable.requestFocus();
					chooseTable.setFocusableInTouchMode(true);
					setContentView(chooseTable);
					break;
			    case START_GAME:
			    	v1 =new MySurfaceView(Pig2Activity.this);
					v1.requestFocus();//獲取焦點
					v1.setFocusableInTouchMode(true);//設置為可觸控
					left = 3;
					toInitMAP();
					toInit();
					DEADTIME_FLAG=true;
			        setContentView(v1);
			        new DeadtimeThread(Pig2Activity.this).start();
			        break;
			    case ENTER_SETTING:
					setting=new Setting(Pig2Activity.this);
					setting.requestFocus();//獲取焦點
				    setting.setFocusableInTouchMode(true);//設置為可觸控
				    setContentView(setting);
				    break;
			    case ENTER_RECORD:
			    	recordTable=new RecordTable(Pig2Activity.this);
			    	recordTable.requestFocus();//獲取焦點
			    	recordTable.setFocusableInTouchMode(true);//設置為可觸控
				    setContentView(recordTable);
				    break;	
			    case ENTER_WINVIEW:
    				winView=new YouWin(Pig2Activity.this);
    				winView.requestFocus();
    				winView.setFocusableInTouchMode(true);
    				setContentView(winView);
    				left = 3;
    				break;
			    case ENTER_MAP:
					makemap=new Makemap(Pig2Activity.this);
					makemap.requestFocus();//獲取焦點
					makemap.setFocusableInTouchMode(true);//設置為可觸控
				    setContentView(makemap);
				    break;
			    case LOADING:
			    	vld=new ViewLoading(Pig2Activity.this);
			    	vld.requestFocus();//獲取焦點
			    	vld.setFocusableInTouchMode(true);//設置為可觸控
				    setContentView(vld);
				    new Thread(){
	        		 public void run(){
	        		  if(!LoadThreeDModel.isLoad)
	        		   LoadThreeDModel.Loading();
	        		  
	        		  if(isTest)
	        		   toAnotherView(ENTER_TEST);
	        		  else
	        		   toAnotherView(START_GAME);//進入遊戲畫面 
	        		  vld.destoryB();
	        		 }
	        		}.start();
	        		
				    break;
			    case ENTER_TEST:
			    	left = 3;
			 	    toInit();
			 	    DEADTIME_FLAG=true;
			 	    isTest = true;
			 	    InitTestMap();
			 	    v1 =new MySurfaceView(Pig2Activity.this);
			 	    v1.requestFocus();//獲取焦點
			 	    v1.setFocusableInTouchMode(true);//設置為可觸控
			 	    setContentView(v1);
			        new DeadtimeThread(Pig2Activity.this).start();
				    break;
			    case ENTER_Pass:
			    	 pass=new  Pass(Pig2Activity.this);
			    	 pass.requestFocus();//獲取焦點
			    	 pass.setFocusableInTouchMode(true);//設置為可觸控
				    setContentView(pass);
				    break;	
				}
			}
		};		
		//waitTwoSeconds();
        hd.sendEmptyMessage(ENTER_MENU);
    }
    
    public void InitTestMap()
    {
     int rows=nowMap.length;
     int cols=nowMap[0].length;
     MAP = new int[rows][cols];
     for(int i=0;i<nowMap.length;i++)
	  for(int j=0;j<nowMap[0].length;j++){
	   MAP[i][j] = nowMap[i][j];
			         }
    }
    
    //重設狀態
    public void toInit()
    {
    	isTest = false;
    	THREAD_FLAG = true;
		winFlag=false;
		loseFlag=false;
		littleMenuNow = false;
		
		score=0;
		finishTime = 0;
		finishLeft = 0;
		seeScoreLeft=false;
		seeScoreTime=false;
		
		if(DEADTIME_RESET)
		{
		 deadtimes = 100;
		 DEADTIME_RESET = false;
		}
    }
    
    //重設地圖
    public void toInitMAP()
    {
     int rows=LevelMap.LMAP[level].length;
     int cols=LevelMap.LMAP[level][0].length;
     MAP = new int[rows][cols];
     
     for(int i=0;i<rows;i++)
      for(int j=0;j<cols;j++)
       {
        MAP[i][j] = LevelMap.LMAP[level][i][j];
       }
	
    }
   
    
    //方向感測
    private SensorEventListener mySensorListener = new SensorEventListener(){
    	
		public void onSensorChanged(SensorEvent e) 
		{
			if (e.sensor.getType() == Sensor.TYPE_ORIENTATION)
			{//判斷是否為方位感測器變化產生的資料	
				float x = e.values[SensorManager.DATA_X];
				float y = e.values[SensorManager.DATA_Y];
				float z = e.values[SensorManager.DATA_Z];
				int directionDotXY[]=RotateUtil.getDirectionDot
						(
								new double[]{x,y,z}
					    );
			 //標準化xy位移量
				double mLength=directionDotXY[0]*directionDotXY[0]+
			            directionDotXY[1]*directionDotXY[1];
			mLength=Math.sqrt(mLength);
			double angle = Math.toRadians(moveAngle);
			 rx =-((int)((directionDotXY[1]/mLength)*200)*6f*(float)(Math.cos(angle))
					 +(int)((directionDotXY[0]/mLength)*200)*6f*(float)(Math.sin(angle)));
			 rz =-((int)((directionDotXY[0]/mLength)*200)*6f*(float)(Math.cos(angle))
					- (int)((directionDotXY[1]/mLength)*200)*6f*(float)(Math.sin(angle)));
			}	
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
    };
    
    public void waitTwoSeconds()
    {
		try {
				Thread.sleep(2500);
			} 
		catch (InterruptedException e) {
				e.printStackTrace();
			}  
    }
    
    //切換method
    public void toAnotherView(int flag)
    {
    	switch(flag)
    	{
    	case 0:hd.sendEmptyMessage(ENTER_MENU);break;
    	case 1:hd.sendEmptyMessage(ENTER_CHOOSE);break;
    	case 2:hd.sendEmptyMessage(START_GAME);break;
    	case 3:hd.sendEmptyMessage(ENTER_SETTING);break;
    	case 4:hd.sendEmptyMessage(ENTER_RECORD);break;
    	case 5:hd.sendEmptyMessage(ENTER_WINVIEW);break;
    	case 6:hd.sendEmptyMessage(ENTER_MAP);break;
    	case 7:hd.sendEmptyMessage(LOADING);break;
    	case 8:hd.sendEmptyMessage(ENTER_TEST);break;
    	case 9:hd.sendEmptyMessage(ENTER_Pass);break;
    	}
    }
    //初始化音樂
    protected void initSound() {
		// TODO Auto-generated method stub
		//背景音樂
    	mpBack=MediaPlayer.create(this,R.raw.top);
    	mpBack.setLooping(true);
    	
    	if(soundFlag)
    	 mpBack.start();
    	
    	mpWin=MediaPlayer.create(this,R.raw.winsound);
    	mpLose=MediaPlayer.create(this,R.raw.lose);
    	
    	mpDrop=MediaPlayer.create(this,R.raw.drop);
    	mpDrop.setLooping(true);
      }
    
    //停止音樂
    protected void stopSound() {
		// TODO Auto-generated method stub
    	mpBack.stop();
	     mpWin.stop();
	     mpLose.stop();
	     mpDrop.stop();
    	
    }
    
  //進入ip地址和port界面
    public void gotoIpView()
    {
     setContentView(R.layout.net);   
     final Button blj=(Button)this.findViewById(R.id.Button01);
     final Button bfh=(Button)this.findViewById(R.id.Button02);
    	
     blj.setOnClickListener
      (
       new  OnClickListener()
       {
	    public void onClick(View v) 
	    {
		 final EditText eta=(EditText)findViewById(R.id.EditText01);
		 final EditText etb=(EditText)findViewById(R.id.EditText02);
		 String ipStr=eta.getText().toString();					
		 String portStr=etb.getText().toString();
		 //ip地址本地驗證
		 String[] ipA=ipStr.split("\\.");
		 if(ipA.length!=4)
		 {
		  Toast.makeText(Pig2Activity.this,
			             "服務器IP地址不合法", 
				         Toast.LENGTH_SHORT
			            ).show();
		  return;
		 }
		 for(String s:ipA)
		 {
		  try {
		   int ipf=Integer.parseInt(s);
		   if(ipf>255||ipf<0)
	       {
		    Toast.makeText(Pig2Activity.this,
		                   "服務器IP地址不合法", 
				           Toast.LENGTH_SHORT
				          ).show();							
			return;
	       }
		  }
		  catch(Exception e) {
		   Toast.makeText(Pig2Activity.this,
			                "服務器IP地址不合法!", 
			                Toast.LENGTH_SHORT).show();							
		   return;
		  }
		 }
	    //端口號本地驗證
		try{
		 int port=Integer.parseInt(portStr);
		 if(port>65535||port<0){
          Toast.makeText(Pig2Activity.this,
			             "服務器端口號不合法!", 
		                 Toast.LENGTH_SHORT).show();							
		 return;
	    }						
	   }
	  catch(Exception e){
	   Toast.makeText(Pig2Activity.this,
		              "服務器端口號不合法!", 
		              Toast.LENGTH_SHORT).show();							
	   return;
	  }	
	 //驗證過關
	 int port=Integer.parseInt(portStr);
	 try{
	  Socket sc=new Socket(ipStr,port);
	  DataInputStream din=new DataInputStream(sc.getInputStream());
	  DataOutputStream dout=new DataOutputStream(sc.getOutputStream());
	  ca=new ClientAgent(Pig2Activity.this,sc,din,dout);
	  ca.start();
	 }
	 catch(Exception e){
	  Toast.makeText(Pig2Activity.this,
	                 "網絡連接失敗,請稍後重試!", 
	                 Toast.LENGTH_SHORT).show();	
	  return;	
     }	
    }    			
   });
  bfh.setOnClickListener (new OnClickListener(){				 
   public void onClick(View v){
	toAnotherView(ENTER_MENU);//進入主菜單
   }    			
  });
   curr=WhichView.IP_VIEW;
 }

   //輸入行數及列數
    
    public void gotoEnterMapname()
    {
     setContentView(R.layout.entermapname);   
     final Button blj=(Button)this.findViewById(R.id.Button05);
     final Button bfh=(Button)this.findViewById(R.id.Button06);
     
     final EditText getName=(EditText)this.findViewById(R.id.EditText03);
     
     blj.setOnClickListener(
      new OnClickListener(){
       public void onClick(View v) {
    	 String name=getName.getText().toString();
    	 DBUtil.insertMap(name, starti, startj, MAP);
    	 Toast.makeText(
    	  Pig2Activity.this,
    	  "地圖已儲存", 
    	  Toast.LENGTH_SHORT).show();
    	 toAnotherView(ENTER_MAP);
       }    			
      });
     
     bfh.setOnClickListener(
      new OnClickListener(){
       public void onClick(View v) {
    	toCreateMap();
       }    			
      });
    }
    
    public void toCreateMap()
    {
    	isPaint = true;
    	setContentView(R.layout.mapmake);
    	cmv=new CreateMapview(Pig2Activity.this);
    	cmv.requestFocus();//獲取焦點
    	cmv.setFocusableInTouchMode(true);//設置為可觸控
    	if(isEdit)
    	 cmv.getMap(nowMap, sPoint[0],sPoint[1]);
    	
    	final Button blj=(Button)this.findViewById(R.id.Button03);
    	final Button bfh=(Button)this.findViewById(R.id.Button04);
    	ListView myListView = (ListView)findViewById(R.id.listView1);
    	
    	
    	myListView.setBackgroundColor(Color.GRAY);
    	
    	ArrayList<String[]> al=new ArrayList<String[]>();
    	al=DBUtil.getResult();
    	
    	ArrayList<String> mapTool=new ArrayList<String>();
    	
    	mapTool.add("道路");
    	mapTool.add("起點");
   	    mapTool.add("終點");
    	
    	if(al.get(0)[3].equals("1"))
    	{
    	 mapTool.add("生命球");
    	 mapTool.add("時間球");
    	}
    	
    	if(al.get(1)[3].equals("1"))
    	{
    	 mapTool.add("碎石地");
    	}
    	
    	if(al.get(2)[3].equals("1"))
    	{
    	 mapTool.add("中繼點");
    	 
    	}
    	if(al.get(3)[3].equals("1"))
    	{
    	 mapTool.add("大樹");
    	 mapTool.add("糖果");
    	}
    	if(al.get(4)[3].equals("1"))
    	{
    	 mapTool.add("雞肉棒");
    	 mapTool.add("小樹");
    	 mapTool.add("冰地板");
    	}
    	
   	    String[] array =new String[mapTool.size()];
   	    for(int i=0;i<mapTool.size();i++)
   	    {
   	     array[i] = mapTool.get(i);
   	    }
 
   	    
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	        android.R.layout.simple_list_item_1, array);
    	
    	myListView.setAdapter(adapter);
    	
    	myListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
          public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
              long arg3)
          {
            /* 使用getSelectedItem()將選取的值帶入myTextView中 */
        	  nowPoint = arg2+1;
          }

          public void onNothingSelected(AdapterView<?> arg0)
          {
            // TODO Auto-generated method stub

          }
        });

    /* myListView加入OnItemClickListener */
    myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
     public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3){
      nowPoint = arg2+1;
        /* 使用String[index]，arg2為點選到ListView的index，並將值帶入myTextView中 */
     }
    });
    
    LinearLayout ly=(LinearLayout)findViewById(R.id.LinearLayout02);
    ly.addView(cmv);
    
    blj.setOnClickListener(new OnClickListener(){
        public void onClick(View v) {
         if(cmv.putstartPoint&&cmv.putendPoint)
         {
          if(isEdit)
          {
           DBUtil.UpdataMap(starti, startj, MAP);
           gotoMapList();
           isEdit = false;
          }
          else
           gotoEnterMapname();
         }
         else
         {
          Toast.makeText(
           Pig2Activity.this,
           "請設定起點與終點", 
           Toast.LENGTH_SHORT
          ).show();
         }
        }    			
       });
    
    bfh.setOnClickListener(new OnClickListener(){
     public void onClick(View v) {
    	 if(isEdit)
    	 {
    	  isEdit = false;
    	  gotoEditMapList();
    	 }
    	 else
          hd.sendEmptyMessage(ENTER_MAP);
     }    			
    });
   }
    
    public void gotochangeMap()
    {
    	setContentView(R.layout.changemap);
    	
    	final Button bjv=(Button)this.findViewById(R.id.Button05);
    	final Button bfh=(Button)this.findViewById(R.id.Button11);
    	ListView myListView = (ListView)findViewById(R.id.listView03);
    	
    	ArrayList<String[]> al=new ArrayList<String[]>();
    	al=DBUtil.getMapResult();
    	String[] array =new String[al.size()];
        
    	
    	for(int i=0;i<al.size();i++)
    	{
    	 array[i] = al.get(i)[0] +"  "+al.get(i)[3];
    	}
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	        android.R.layout.simple_list_item_1, array);
    	
    	myListView.setAdapter(adapter);
    	
    	myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
    	 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3){
    		 ArrayList<String[]> al=new ArrayList<String[]>();
    	     al=DBUtil.getMapResult();
    	 }
    	});
    	
    	
    	
        bfh.setOnClickListener(
           	 new OnClickListener(){
       		  public void onClick(View v) {
       			hd.sendEmptyMessage(ENTER_SETTING);
       		  }    			
                }
               );
    }
    
    public void gotoMapList()
    {
    	setContentView(R.layout.maplist);
    	
    	final Button bfh=(Button)this.findViewById(R.id.Button06);
    	ListView myListView = (ListView)findViewById(R.id.listView02);
    	
    	ArrayList<String[]> al=new ArrayList<String[]>();
    	al=DBUtil.getMapResult();
    	String[] array =new String[al.size()];
        
    	
    	for(int i=0;i<al.size();i++)
    	{
    	 array[i] = al.get(i)[0] +"  "+al.get(i)[3];
    	}
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	        android.R.layout.simple_list_item_1, array);
    	
    	myListView.setAdapter(adapter);
    	
    	myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
    	 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3){
    		 ArrayList<String[]> al=new ArrayList<String[]>();
    	     al=DBUtil.getMapResult();
    	     String mapArray = al.get(arg2)[2];
    	     String startArray = al.get(arg2)[1];
    	     keyS = al.get(arg2)[3];
    	     nowMap = new int[25][22];
    	     sPoint = new int[2];
    	     int i=0;
    		 int j=0;
    		 int count = 0;
    		 int num = 0;
    		 while(true)
    		 {
    		  char c = mapArray.charAt(count);
    		  if(c>='0' && c <='9')
    		  {
    		   num*=10;
    		   num+=Integer.parseInt(String.valueOf(c));
    		  }
    		  else
    		  {
    		   nowMap[i][j] = num;
    		   num=0;
    		   j++;
    		   if(j==22)
    		   {
    			j=0;
    			i++;
    		   }
    		  }
    		  count++;
    		  if(count==mapArray.length())
    		  {
    		   nowMap[i][j] = num;
    		   break;
    		  }
    		 }
    		 count = 0;
    		 i = 0;
    		 while(true)
    		 {
    		  char c = startArray.charAt(count);
    		  if(c>='0' && c <='9')
    		  {
    		   num*=10;
    		   num+=Integer.parseInt(String.valueOf(c));
    		  }
    		  else
    		  {
    		   sPoint[i] = num;
    		   num=0;
    		   i++;
    		   if(i==sPoint.length)
    			break;
    		  }
    		  count++;
    		  if(count==startArray.length())
    		  {
    		   sPoint[i] = num;
    		   break;
    		  }
    		 }
    		 starti = sPoint[0];
    		 startj = sPoint[1];
    		 gotoEditMapList();
    	   /* 使用String[index]，arg2為點選到ListView的index，並將值帶入myTextView中 */
    	 }
    	});
    	
    	
    	
        bfh.setOnClickListener(
           	 new OnClickListener(){
       		  public void onClick(View v) {
       			hd.sendEmptyMessage(ENTER_MAP);
       		  }    			
                }
               );
    }
    
    public void gotoEditMapList()
    {
     setContentView(R.layout.mapedit);
     isPaint = false;
    	
     final Button b1=(Button)this.findViewById(R.id.Button07);
     final Button b2=(Button)this.findViewById(R.id.Button08);
     final Button b3=(Button)this.findViewById(R.id.Button09);
     final Button b4=(Button)this.findViewById(R.id.Button10);
     
     cmv=new CreateMapview(Pig2Activity.this);
 	 cmv.requestFocus();//獲取焦點
 	 cmv.setFocusableInTouchMode(false);//設置為可觸控
  	 cmv.getMap(nowMap, sPoint[0],sPoint[1]);
 	 LinearLayout ly=(LinearLayout)findViewById(R.id.LinearLayout02);
     ly.addView(cmv);
     
     b1.setOnClickListener(new OnClickListener(){
      public void onClick(View v) {
    	  isTest = true;
    	 toAnotherView(LOADING);
      }    			
     });
     b2.setOnClickListener(new OnClickListener(){
         public void onClick(View v) {
          isEdit = true;
          toCreateMap();
         }    			
        });
     b3.setOnClickListener(new OnClickListener(){
         public void onClick(View v) {
          DBUtil.deleteMap();
          gotoMapList();
         }    			
        });
     b4.setOnClickListener(new OnClickListener(){
      public void onClick(View v) {
       gotoMapList();
      }    			
     });
    }
    
    
    @Override
	protected void onResume() {	//重寫onResume方法
    	super.onResume();
       List<Sensor> sensors = mySensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
    	if (sensors.size() > 0)
    	{
    	Sensor sensor = sensors.get(0);
    	
    	mySensorManager.registerListener(mySensorListener,
    			sensor, SensorManager.SENSOR_DELAY_GAME);
    	}
	}
	
	@Override
	protected void onPause() {									//重寫onPause方法
		super.onPause();
		 mySensorManager.unregisterListener(mySensorListener); 
		 mpBack.pause();
	     mpWin.pause();
	     mpLose.pause();
	     mpDrop.pause();
	}
}
