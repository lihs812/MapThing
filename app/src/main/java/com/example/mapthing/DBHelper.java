package com.example.mapthing;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "mapthing_object.db";

    // DBHelper 생성자
    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    // Person Table 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블을 생성합니다.
        // ID, TITLE, PATH, TAG, WRITEDATE, ALARMTYPE, ALARMTIME 컬럼을 가지고 있습니다.
        // ID는 자동으로 증가하는 PRIMARY KEY 컬럼이고, 나머지 컬럼은 TEXT 혹은 INT 타입입니다.
        db.execSQL("CREATE TABLE object(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, PATH TEXT, TAG TEXT, WRITEDATE TEXT, ALARMTYPE INT, ALARMTIME TEXT)");
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
                + "VALUES('" + title + "', '" + path + "', '" + tag + "', '" + writeDate + "' , '" + alarmType + "' , '" + alarmTime + "')");
        db.close();
    }

    // Person Table 데이터 수정
    public void update(String title, String path, String tag, String writeDate, int alarmType, String alarmTime) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE object SET PATH = '" + path + "', TAG = '" + tag + "', WRITEDATE = '"+ writeDate + "', ALARMTYPE = " + alarmType + ", ALARMTIME = '" + alarmTime + "' WHERE TITLE = '" + title + "'");
        db.close();
    }

    // Person Table 데이터 삭제
    public void delete(String title) {
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
                    + cursor.getString(0)   // TITLE 컬럼 데이터
                    + "\n path : "
                    + cursor.getString(1)   // PATH 컬럼 데이터
                    + "\n tag : "
                    + cursor.getString(2)   // TAG 컬럼 데이터
                    + "\n writeDate : "
                    + cursor.getString(3)   // WRITEDATE 컬럼 데이터
                    + "\n alarmType : "
                    + cursor.getInt(4)      // ALARMTYPE 컬럼 데이터
                    + "\n alarmTime : "
                    + cursor.getString(5)   // ALARMTIME 컬럼 데이터
                    + "\n";
        }

        return result;
    }

    //Table 내의 모든 항목을 리스트의 형태로 반환
//    public List<Map<String, String>> getArray(){
//        List<Map<String, String>> list = new ArrayList<>();
//
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM object", null);
//        try {
//            while (cursor.moveToNext()) {
//                Map<String, String> map = new HashMap<>();
//                map.put("title", cursor.getString(cursor.getColumnIndex("TITLE")));
//                map.put("path", cursor.getString(cursor.getColumnIndex("PATH")));
//                map.put("tag", cursor.getString(cursor.getColumnIndex("TAG")));
//                map.put("writeDate", cursor.getString(cursor.getColumnIndex("WRITEDATE")));
//                map.put("alarmType", cursor.getString(cursor.getColumnIndex("ALARMTYPE")));
//                map.put("alarmTime", cursor.getString(cursor.getColumnIndex("ALARMTIME")));
//                list.add(map);
//            }
//        } finally {
//            cursor.close(); // Cursor를 닫아줌
//        }
//
//        return list;
//    }

    /**
     * DBHelper 클래스에서 Person Table의 모든 데이터를 조회하여 Arritem 객체 리스트 형태로 반환하는 메서드입니다.
     * Arritem은 데이터베이스에서 가져온 각 행의 정보를 담는 클래스입니다.
     *
     * @return Arritem 객체 리스트
     */
    public ArrayList<Arritem> getAlist() {
        ArrayList<Arritem> arritemList = new ArrayList<>();

        // 읽기가능한 데이터베이스를 가져옵니다.
        SQLiteDatabase db = getReadableDatabase();

        // object 테이블의 모든 데이터를 WRITEDATE 기준으로 내림차순으로 조회합니다.
        Cursor cursor = db.rawQuery("SELECT * FROM object ORDER BY WRITEDATE DESC", null);

        // 조회된 데이터가 있을 경우에만 처리합니다.
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                // 컬럼 인덱스를 가져옵니다.
                int titleColumnIndex = cursor.getColumnIndex("TITLE");
                int pathColumnIndex = cursor.getColumnIndex("PATH");
                int tagColumnIndex = cursor.getColumnIndex("TAG");
                int writeDateColumnIndex = cursor.getColumnIndex("WRITEDATE");
                int alarmTypeColumnIndex = cursor.getColumnIndex("ALARMTYPE");
                int alarmTimeColumnIndex = cursor.getColumnIndex("ALARMTIME");

                // 컬럼 인덱스가 올바르게 조회된 경우에만 처리합니다.
                if (titleColumnIndex != -1 && pathColumnIndex != -1 && tagColumnIndex != -1
                        && writeDateColumnIndex != -1 && alarmTypeColumnIndex != -1 && alarmTimeColumnIndex != -1) {
                    // 컬럼 인덱스를 사용하여 해당 컬럼의 데이터를 가져옵니다.
                    String title = cursor.getString(titleColumnIndex);
                    String path = cursor.getString(pathColumnIndex);
                    String tag = cursor.getString(tagColumnIndex);
                    String writeDate = cursor.getString(writeDateColumnIndex);
                    int alarmType = cursor.getInt(alarmTypeColumnIndex);
                    String alarmTime = cursor.getString(alarmTimeColumnIndex);

                    // Arritem 객체를 생성하고 데이터를 설정합니다.
                    Arritem arritem = new Arritem();
                    arritem.setTitle(title);
                    arritem.setPath(path);
                    arritem.setTag(tag);
                    arritem.setWriteDate(writeDate);
                    arritem.setAlarmType(alarmType);
                    arritem.setAlarmTime(alarmTime);

                    // 리스트에 Arritem 객체를 추가합니다.
                    arritemList.add(arritem);
                } else {
                    // 컬럼 인덱스를 찾을 수 없는 경우에 대한 처리
                }
            }
        }

        cursor.close();
        return arritemList;
    }

}