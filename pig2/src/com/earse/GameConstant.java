package com.earse;

public class GameConstant {
	
	public final static float SKY_ANGLE_SPAN=11.25f;//天空角度
	
	public static boolean threadFlag=true;
	
	public static float MENUY =2f;
	public static boolean littleMenuNow =false;
	public static boolean littleMenustart = false;
	public static boolean MenuUp = false;
	public static boolean MenuDown = false;
	
	public static int status=-1;//0:startgames,1:setting,2:about,3:help,4:exit
	public static int keyStatus=status;
	
	public static int left = 3;
	
	public static int level = 0;
	
	//Message編號
	public static final int ENTER_MENU=0; //載入主選單
	public static final int ENTER_CHOOSE=1; //載入選關
    public static final int START_GAME=2;//載入並開始遊戲
    public static final int ENTER_SETTING=3; //載入設定
    public static final int	ENTER_RECORD=4; //載入紀錄
    public static final int ENTER_WINVIEW=5;//進入獲勝頁面
    public static final int ENTER_MAP=6;//製作地圖介面 
    public static final int LOADING = 7; //
    public static final int ENTER_TEST = 8;
    public static final int ENTER_Pass = 9;
    
    //攝影機
    public static float tX;//目標點的初始座標。
    public static float tZ;
    public static float tY;
    public static float cX;
    public static float cY;
    public static float cZ;
    
	//平臺的高
	public static final float HEIGHT=0.1f;
	
	//球半徑
	public static final float SCALE=0.5f;
	//重力加速度
	public static final float G=0.02f;
	//執行緒控制標誌位元
	public static boolean THREAD_FLAG=true;
	//時間間隔
	public static float TIME_SPAN=0.01f;
	//衰減係數
	public static float ANERGY_LOST=0.7f;
	//速度閾值
	public static float MIN_SPEED=0.1f;
	public static float MAX_SPEED=8f;
	//地圖矩陣
	
    //初始放置位置
    public static int INIT_I=0;//所在的列  
    public static int INIT_J=2;//所在的行
    
    public static int pigstate = 0;

    
    public static int[][] MAP;
     
    public static float rx;//球在X軸方向的速度
	public static float rz;//球在Y軸方向的速度
	
	public static int deadtimes=100;//遊戲倒計時
	public static boolean DEADTIME_FLAG=false;//倒計時執行緒標記
	public static boolean DEADTIME_RESET=false;//時間是否重置
	
	//儀錶板中單個數位的大小
	public static final float SCORE_NUMBER_SPAN_X=0.3f;
	public static final float SCORE_NUMBER_SPAN_Y=0.36f;
    
    
	public static boolean soundFlag=false;//是否開啟聲音標誌
	public static int soundSetFlag=0;//設置畫面聲音控制標誌,1 音樂開啟  2  音樂關閉
    
	public static float tempFlag=LevelMap.LMAP[level][0].length/2;//標記地圖的初始位置
    
	public static float ratio;//記錄螢幕高寬比
	public static float backWidth;//記錄螢幕寬度
	public static float backHeight;//記錄螢幕高度
	
    public static final float UNIT_SIZE=1.0f;   //介面常數
	public static final int GV_UNIT_NUM=36;     //一塊地板的頂點數量
	public static final float UNIT_HIGHT=0.1f;   //地板的高度
	
	public static float angleZ = 180f; //攝影機角度X
    public static float angleX = 180f; //攝影機角度Z
    public static double moveAngle = (angleX + angleZ)/2; //合腳
    
	public static boolean winFlag=false;//是否獲勝
	public static boolean loseFlag=false;//是否失敗
	
	public static int score=0; //得分
	public static int finishTime = 0; //剩餘時間
	public static int finishLeft = 0; //剩餘
	
	public static boolean seeScoreLeft=false;
	public static boolean seeScoreTime=false;
	
	public static boolean exitScoreTable=false;
	public static boolean startScoreTable=false;
	
	public static int nowPoint = 1;
	public static int starti = 0;
	public static int startj = 0;
	
	public static boolean isEdit = false;
	public static boolean isPaint = false;
	public static boolean isTest = false;
	public static int[][] nowMap;
	public static String keyS;
	public static int[] sPoint;
	
	public static int createCol=0;//行數
	public static int createRow=0;//列數
	
}
