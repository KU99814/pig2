package com.earse;

public class GameConstant {
	
	public final static float SKY_ANGLE_SPAN=11.25f;//�ѪŨ���
	
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
	
	//Message�s��
	public static final int ENTER_MENU=0; //���J�D���
	public static final int ENTER_CHOOSE=1; //���J����
    public static final int START_GAME=2;//���J�ö}�l�C��
    public static final int ENTER_SETTING=3; //���J�]�w
    public static final int	ENTER_RECORD=4; //���J����
    public static final int ENTER_WINVIEW=5;//�i�J��ӭ���
    public static final int ENTER_MAP=6;//�s�@�a�Ϥ��� 
    public static final int LOADING = 7; //
    public static final int ENTER_TEST = 8;
    public static final int ENTER_Pass = 9;
    
    //��v��
    public static float tX;//�ؼ��I����l�y�СC
    public static float tZ;
    public static float tY;
    public static float cX;
    public static float cY;
    public static float cZ;
    
	//���O����
	public static final float HEIGHT=0.1f;
	
	//�y�b�|
	public static final float SCALE=0.5f;
	//���O�[�t��
	public static final float G=0.02f;
	//���������лx�줸
	public static boolean THREAD_FLAG=true;
	//�ɶ����j
	public static float TIME_SPAN=0.01f;
	//�I��Y��
	public static float ANERGY_LOST=0.7f;
	//�t���H��
	public static float MIN_SPEED=0.1f;
	public static float MAX_SPEED=8f;
	//�a�ϯx�}
	
    //��l��m��m
    public static int INIT_I=0;//�Ҧb���C  
    public static int INIT_J=2;//�Ҧb����
    
    public static int pigstate = 0;

    
    public static int[][] MAP;
     
    public static float rx;//�y�bX�b��V���t��
	public static float rz;//�y�bY�b��V���t��
	
	public static int deadtimes=100;//�C���˭p��
	public static boolean DEADTIME_FLAG=false;//�˭p�ɰ�����аO
	public static boolean DEADTIME_RESET=false;//�ɶ��O�_���m
	
	//�����O����ӼƦ쪺�j�p
	public static final float SCORE_NUMBER_SPAN_X=0.3f;
	public static final float SCORE_NUMBER_SPAN_Y=0.36f;
    
    
	public static boolean soundFlag=false;//�O�_�}���n���лx
	public static int soundSetFlag=0;//�]�m�e���n������лx,1 ���ֶ}��  2  ��������
    
	public static float tempFlag=LevelMap.LMAP[level][0].length/2;//�аO�a�Ϫ���l��m
    
	public static float ratio;//�O���ù����e��
	public static float backWidth;//�O���ù��e��
	public static float backHeight;//�O���ù�����
	
    public static final float UNIT_SIZE=1.0f;   //�����`��
	public static final int GV_UNIT_NUM=36;     //�@���a�O�����I�ƶq
	public static final float UNIT_HIGHT=0.1f;   //�a�O������
	
	public static float angleZ = 180f; //��v������X
    public static float angleX = 180f; //��v������Z
    public static double moveAngle = (angleX + angleZ)/2; //�X�}
    
	public static boolean winFlag=false;//�O�_���
	public static boolean loseFlag=false;//�O�_����
	
	public static int score=0; //�o��
	public static int finishTime = 0; //�Ѿl�ɶ�
	public static int finishLeft = 0; //�Ѿl
	
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
	
	public static int createCol=0;//���
	public static int createRow=0;//�C��
	
}
