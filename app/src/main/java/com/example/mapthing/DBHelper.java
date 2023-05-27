package com.example.test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "object.db";

    // DBHelper 생성자
    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    // Person Table 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Person(LINK TEXT, NAME TEXT, TAG TEXT, ALARM TEXT, TIME INT)");
    }

    // Person Table Upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Person");
        onCreate(db);
    }

    // Person Table 데이터 입력
    public void insert(String link, String name, String tag, int alarm, int time) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Person VALUES('" + link + "', '" + name + "', '" + tag + "', '" + alarm + "' , '" + time + "')");
        db.close();
    }

    // Person Table 데이터 수정
    public void Update(String link, String name, String tag, int alarm, int time) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Person SET LINK = " + link + ", TAG = " + tag + "ALARM = "+ alarm + "TIME = " + time + " WHERE NAME = " + name + "'");
        db.close();
    }

    // Person Table 데이터 삭제
    public void Delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE Person WHERE NAME = '" + name + "'");
        db.close();
    }

    // Person Table 조회
    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM Person", null);
        while (cursor.moveToNext()) {
            result += " link : "
                    + cursor.getString(0)
                    + "\n name : "
                    + cursor.getString(1)
                    + "\n 태그 : "
                    + cursor.getString(2)
                    + "\n 알람 : "
                    + cursor.getInt(3)
                    + "\n 시간 : "
                    +cursor.getInt(4)
                    +"\n";
        }

        return result;
    }
}