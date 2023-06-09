package com.example.mapthing;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "mapthing_object.db";

    // DBHelper 생성자
    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    // Person Table 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE object(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE NTEXT, PATH NTEXT, TAG NTEXT, WRITEDATE NTEXT, ALARMTYPE INT, ALARMTIME NTEXT)");

    }

    // Person Table Upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db , int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS object");
        onCreate(db);
    }

    // Person Table 데이터 입력
    public void insert(String title, String path, String tag, String writeDate, int alarmType, String alarmTime) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO object (TITLE, PATH, TAG, WRITEDATE, ALARMTYPE, ALARMTIME) "
                + "VALUES('" + title + "','" + path + "','" + tag + "','" + writeDate + "'," + alarmType + ",'" + alarmTime + "')");
        db.close();
    }

    // Person Table 데이터 수정
    public void Update(String title, String path, String tag, String writeDate, int alarmType, String alarmTime) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE object SET PATH = '" + path + "', TAG = '" + tag + "', WRITEDATE = '"+ writeDate + "', ALARMTYPE = " + alarmType + ", ALARMTIME = '" + alarmTime + "' WHERE TITLE = '" + title + "'");
        db.close();
    }


    // Person Table 데이터 삭제
    public void Delete(String title) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM object WHERE TITLE = '" + title + "'");
        db.close();
    }

    // Person Table 조회
    public String getString() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM object", null);
        while (cursor.moveToNext()) {
            result += " title : "
                    + cursor.getString(0)
                    + "\n path : "
                    + cursor.getString(1)
                    + "\n tag : "
                    + cursor.getString(2)
                    + "\n writeDate : "
                    + cursor.getString(3)
                    + "\n alarmType : "
                    + cursor.getInt(4)
                    + "\n alarmTime : "
                    + cursor.getString(5)
                    + "\n";
        }
        cursor.close();
        return result;
    }
    //한 객체의 모든 경로를 보여주는 함수
    //안에 타이틀의 이름을 넣으면 연결된 모든 경로를 보여줌
    public List<String> getPath(String title){
        SQLiteDatabase db = getReadableDatabase();
        List<String> list = new ArrayList<>();
        list.add(title);
        String t = null;
        Cursor cursor = db.rawQuery("SELECT * FROM object WHERE TITLE = '" + title + "'", null);
        if (cursor.moveToFirst()) {
             t = cursor.getString(2);
        }
        cursor.close();
        if (t != null) {
            list.addAll(getPath(t));
        }
        return list;
    }

    public ArrayList<Arritem> getAlist() {
        ArrayList<Arritem> arritemList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM object ORDER BY WRITEDATE DESC", null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                int titleColumnIndex = cursor.getColumnIndex("TITLE");
                int pathColumnIndex = cursor.getColumnIndex("PATH");
                int tagColumnIndex = cursor.getColumnIndex("TAG");
                int writeDateColumnIndex = cursor.getColumnIndex("WRITEDATE");
                int alarmTypeColumnIndex = cursor.getColumnIndex("ALARMTYPE");
                int alarmTimeColumnIndex = cursor.getColumnIndex("ALARMTIME");

                if (titleColumnIndex != -1 && pathColumnIndex != -1 && tagColumnIndex != -1
                        && writeDateColumnIndex != -1 && alarmTypeColumnIndex != -1 && alarmTimeColumnIndex != -1) {
                    String title = cursor.getString(titleColumnIndex);
                    String path = cursor.getString(pathColumnIndex);
                    String tag = cursor.getString(tagColumnIndex);
                    String writeDate = cursor.getString(writeDateColumnIndex);
                    int alarmType = cursor.getInt(alarmTypeColumnIndex);
                    String alarmTime = cursor.getString(alarmTimeColumnIndex);

                    Arritem arritem = new Arritem();
                    arritem.setTitle(title);
                    arritem.setPath(path);
                    arritem.setTag(tag);
                    arritem.setWriteDate(writeDate);
                    arritem.setAlarmType(alarmType);
                    arritem.setAlarmTime(alarmTime);

                    arritemList.add(arritem);
                    // 아래 코드를 추가하여 item_path.xml에 경로를 설정합니다.
                    arritem.setPath(path);
                } else {
                    // 컬럼 인덱스를 찾을 수 없는 경우에 대한 처리
                }
            }
        }
        cursor.close();
        return arritemList;
    }
}