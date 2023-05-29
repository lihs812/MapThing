package com.example.mapthing;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static SQLiteDatabase mDB;
    private RecyclerView mRv_mapthings;
    private FloatingActionButton mBtn_write;
    private ArrayList<Arritem> mAlist;
    private GETDB mGETDB;
    private Context mContext; // mContext 변수 추가



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();
    }


    private void setInit() {
        // GETDB 인스턴스 생성
        mGETDB = new GETDB(this);

        // RecyclerView 및 FloatingActionButton 요소 초기화
        mRv_mapthings = findViewById(R.id.rv_list);
        mBtn_write = findViewById(R.id.floatingActionButton4);
        mAlist = new ArrayList<>();

        //FloatingActionButton 클릭 리스너
        mBtn_write.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 팝업창
                Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.popup_edit);
                EditText plain_text1 = dialog.findViewById(R.id.plain_text1);
                EditText tag_name = dialog.findViewById(R.id.tag_name);
                Button btn_ok = dialog.findViewById(R.id.btn_ok);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                        mGETDB.InsertDATA(
                                plain_text1.getText().toString(),
                                currentTime,
                                tag_name.getText().toString(),
                                false,
                                "",
                                ""
                        );
                    }
                });
                dialog.show();
            }
        });
    }
}
