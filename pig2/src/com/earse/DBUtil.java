package com.earse;
import java.util.ArrayList;
import java.util.Date;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import static com.earse.GameConstant.*;

public class DBUtil{
	static SQLiteDatabase sld;	
	public static void createOrOpenDatabase()
	{
		try
    	{
	    	sld=SQLiteDatabase.openDatabase
	    	(
	    	 "/data/data/com.earse/mydb2", //資料庫所在路徑
	    	 null, 								//游標工廠
	    	 SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY //讀寫、若不存在則建立
	    	);	    		
	    	String sql="create table if not exists thedata (name int,open int,highscore int,clean int)";
	    	sld.execSQL(sql);
    	}
    	catch(Exception e)
    	{
    		Log.d("wrong", "wrong");
    		e.printStackTrace();
    	}
	}
	
	public static void createMapTable()
	{
		try
    	{
		 createOrOpenDatabase();
	     String sql="create table if not exists themap (name varchar2(1000) , startpoint varchar2(10) ,"+
		           " mapdata varchar2(5000) , createdate varchar2(40))";
	     sld.execSQL(sql);
	     closeDatabase();
    	}
    	catch(Exception e)
    	{
    		Log.d("wrong", "wrong");
    		e.printStackTrace();
    	}
	}
	
	public static void insertMap(String name,int starti,int startj,int[][] insertmap)
    {
	 StringBuilder mapS=new StringBuilder();
	 StringBuilder pointS=new StringBuilder();
	 
	 for(int i=0;i<insertmap.length;i++)
	  for(int j=0;j<insertmap[0].length;j++)
	  {
	   mapS.append(insertmap[i][j]);
	   
	   if(i==insertmap.length-1&&j==insertmap[0].length-1)
		break;
	   mapS.append("-");
	  }
	 
	 pointS.append(starti);
 	 pointS.append("-");
 	 pointS.append(startj);
	
 	 
 	 Date date=new Date();
	 StringBuilder sb=new StringBuilder();
	 sb.append(date.getYear()+1900);
	 sb.append("-");
	 sb.append((date.getMonth()>8)?(date.getMonth()+1):"0"+(date.getMonth()+1));
	 sb.append("-");
	 sb.append((date.getDate()>9)?(date.getDate()):("0"+date.getDate()));
	 sb.append(" ");
	 sb.append((date.getHours()>9)?(date.getHours()):("0"+date.getHours()));
	 sb.append(":");
	 sb.append((date.getMinutes()>9)?(date.getMinutes()):("0"+date.getMinutes()));
	 sb.append(":");
	 sb.append((date.getSeconds()>9)?(date.getSeconds()):("0"+date.getSeconds()));
 	  
     try
     {
      createOrOpenDatabase();
      String sql="insert into themap values('"+name+"','"+pointS+"','"+mapS+"','"+sb+"')";
      Log.d("sql", sql+"");
      sld.execSQL(sql);
      closeDatabase();  
     }
	 catch(Exception e)
	 {
	  e.printStackTrace();
	 }	
    }
	
	//關閉資料庫的方法
    public static void closeDatabase()
    {
    	try
    	{
	    	sld.close();     		
    	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    //與資料庫中資料比較
    public static void getNewRecord()
    {	
    	ArrayList<String[]> al=new ArrayList<String[]>();
    	al=getResult();
    	try
    	{
    	 createOrOpenDatabase();
    	 int oldScore = Integer.parseInt(al.get(level)[2]);
    	 if(score>oldScore)
    	 {
         String sql2 = "update thedata set highscore = "+score+" where name = "+(level+1);
         sld.execSQL(sql2);
    	 }
        closeDatabase();   
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public static void UpdataLevel()
    {
     try{
      createOrOpenDatabase();
      String sql = "update thedata set clean = 1 where name = "+(level+1); 
      sld.execSQL(sql);
      if(level<(LevelMap.LMAP.length-1)){
       String sql2 = "update thedata set open = 1 where name = "+(level+2); 
       sld.execSQL(sql2);
      }
      closeDatabase();
 	 }
     catch(Exception e){
	  e.printStackTrace();
	 } 
    }

    //插入記錄的方法
    public static void insert(int Level,int open,int highscore,int clean)
    {	
     try
     {
      createOrOpenDatabase();
      String sql="insert into thedata values("+Level+","+open+","+highscore+","+clean+")";
      Log.d("sql", sql+"");
      sld.execSQL(sql);
      closeDatabase();  
     }
	 catch(Exception e)
	 {
	  e.printStackTrace();
	 }	
    }
    //獲取整個列陣
  //查詢的方法
    public static ArrayList<String[]> getResult()
    {
    	ArrayList<String[]> al=new ArrayList<String[]>();    	
    	try
    	{
    		createOrOpenDatabase();//打開資料庫
    		String sql="select name,open,highscore,clean from thedata order by name";
    		Cursor cur=sld.rawQuery(sql, new String[]{});
    		while(cur.moveToNext())
    		{
    			String[] ta=new String[4];
    			int thislevel=cur.getInt(0);
    			ta[0]=thislevel+"";
                ta[1]=cur.getString(1);
                ta[2]=cur.getString(2);
                ta[3]=cur.getString(3);
    			al.add(ta);
    		}
    		cur.close();
        	closeDatabase();
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}    	
    	return al;
    }
    
    public static ArrayList<String[]> getMapResult()
    {
    	ArrayList<String[]> al=new ArrayList<String[]>();    	
    	try{
    	 createOrOpenDatabase();//打開資料庫
    	 String sql="select name,startpoint,mapdata,createdate from themap order by createdate DESC";
    	 Cursor cur=sld.rawQuery(sql, new String[]{});
    	 while(cur.moveToNext()){
    	  String[] ta=new String[4];
    	  ta[0]=cur.getString(0);
          ta[1]=cur.getString(1);
          ta[2]=cur.getString(2);
          ta[3]=cur.getString(3);
    	  al.add(ta);
    	 }
    	 cur.close();
         closeDatabase();
    	}
    	catch(Exception e){
    	 e.printStackTrace();
    	}    	
    	return al;
    }
    
    public static void UpdataMap(int starti,int startj,int[][] insertmap)
    {
	 StringBuilder mapS=new StringBuilder();
	 StringBuilder pointS=new StringBuilder();
	 
	 for(int i=0;i<insertmap.length;i++)
	  for(int j=0;j<insertmap[0].length;j++)
	  {
	   mapS.append(insertmap[i][j]);
	   
	   if(i==insertmap.length-1&&j==insertmap[0].length-1)
		break;
	   mapS.append("-");
	  }
	 
	 pointS.append(starti);
 	 pointS.append("-");
 	 pointS.append(startj);
	
 	 
 	 Date date=new Date();
	 StringBuilder sb=new StringBuilder();
	 sb.append(date.getYear()+1900);
	 sb.append("-");
	 sb.append((date.getMonth()>8)?(date.getMonth()+1):"0"+(date.getMonth()+1));
	 sb.append("-");
	 sb.append((date.getDate()>9)?(date.getDate()):("0"+date.getDate()));
	 sb.append(" ");
	 sb.append((date.getHours()>9)?(date.getHours()):("0"+date.getHours()));
	 sb.append(":");
	 sb.append((date.getMinutes()>9)?(date.getMinutes()):("0"+date.getMinutes()));
	 sb.append(":");
	 sb.append((date.getSeconds()>9)?(date.getSeconds()):("0"+date.getSeconds()));
 	  
     try
     {
      createOrOpenDatabase();
      String sql="update themap set startpoint ='"+pointS+"',mapdata = '"+mapS+"',createdate = '"+sb+"' "+
                 "where createdate = '"+keyS+"'";
      Log.d("sql", sql+"");
      sld.execSQL(sql);
      closeDatabase();  
     }
	 catch(Exception e)
	 {
	  e.printStackTrace();
	 }	
    }
    
    public static void deleteMap()
    {
    	try
     	{
         createOrOpenDatabase();
         String sql = "delete from themap where createdate = '"+keyS+"'"; 
         sld.execSQL(sql);
         Log.d("sql", sql+"");
     	}catch(Exception e)
    	{
    		e.printStackTrace();
    		
    	}
    }
}

