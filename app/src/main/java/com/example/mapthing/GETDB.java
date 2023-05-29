package com.example.mapthing;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class GETDB extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "MapThings";

    public GETDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db가 생성이 될 때 호출
        db.execSQL("Create TABLE IF NOT EXISTS AList (id INTERER PRIMARY KEY AUTOINCREMENT, customTitle TEXT NOT NULL, realPath TEXT NOT NULL,category TEXT, status TEXT, tag TEXT, writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    //53:56
    //select 쿼리문
    public ArrayList<Arritem> getAlist() {
        ArrayList<Arritem> arritem = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM AList ORDER BY writeDate DESC", null);
        if (cursor.getCount() != 0) {
            // 조회한 데이터가 있을 때 내부에서 실행된다
            while (cursor.moveToNext()) {
                int idColumnIndex = cursor.getColumnIndex("id");
                int customTitleColumnIndex = cursor.getColumnIndex("customTitle");
                int realPathColumnIndex = cursor.getColumnIndex("realPath");
                int categoryColumnIndex = cursor.getColumnIndex("category");
                int statusColumnIndex = cursor.getColumnIndex("status");
                int tagColumnIndex = cursor.getColumnIndex("tag");

                if (idColumnIndex != -1 && customTitleColumnIndex != -1 && realPathColumnIndex != -1
                        && categoryColumnIndex != -1 && statusColumnIndex != -1 && tagColumnIndex != -1) {
                    int id = cursor.getInt(idColumnIndex);
                    String customTitle = cursor.getString(customTitleColumnIndex);
                    String realPath = cursor.getString(realPathColumnIndex);
                    String category = cursor.getString(categoryColumnIndex);
                    int statusValue = cursor.getInt(statusColumnIndex);
                    boolean status = (statusValue == 1); // 1일 때 true, 0일 때 false로 변환
                    String tag = cursor.getString(tagColumnIndex);

                    Arritem arriteM = new Arritem();
                    arriteM.setId(id);
                    arriteM.setCustomTitle(customTitle);
                    arriteM.setRealPath(realPath);
                    arriteM.setCategory(category);
                    arriteM.setStatus(status);
                    arriteM.setTag(tag);

                    arritem.add(arriteM);  // 리스트에 데이터 추가

                    // 나머지 코드...
                } else {
                    // 컬럼 인덱스를 찾을 수 없는 경우에 대한 처리
                }
            }
        }

        return arritem;
    }



    //insert 쿼리문
    public void InsertDATA(String _customTitle, String _realPath, String _category, boolean _status, String _tag, String _writeDate)    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT " +
                   "INTO Alist (customTitle, realPath, category, status, tag) " +
                   "VALUES('" + _customTitle + "','" + _customTitle + "', '" + _realPath + "', '" + _category + "', '" + _status + "', '" + _tag + "', '" + _writeDate + "');");

    }

    //update 쿼리문
    public void UpdateDATA(String _customTitle, String _realPath, String _category, boolean _status, String _tag, String _writeDate, String _id)    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE AList " +
                   "SET customtitle='" + _customTitle + "', realPath='" + _realPath + "', category='" + _category + "', status='" + _status + "', tag='" + _tag + "', writeDate='" + _writeDate + "'WHERE id='" + _id + "'");

    }

    //delete 쿼리문
    public void deleteDATA(int _id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM AList WHERE id = '" + _id + "'");
    }
}


