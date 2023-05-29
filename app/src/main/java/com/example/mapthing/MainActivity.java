package com.example.mapthing;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static SQLiteDatabase mDB;
    private RecyclerView mRv_mapthings;
    private FloatingActionButton mBtn_write;
    private ArrayList<Arritem> mAlist;
    private DBHelper dbHelper;
    private Context mContext; // mContext 변수 추가
    private CustomAdapter mAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_main);
        mContext = this;
        setInit();
    }


    private void setInit() {
        // GETDB 인스턴스 생성
        dbHelper = new DBHelper(MainActivity.this, 2);

        // RecyclerView 및 FloatingActionButton 요소 초기화
        mRv_mapthings = findViewById(R.id.rv_list);
        mBtn_write = findViewById(R.id.floatingActionButton4);
        mAlist = new ArrayList<>();

        loadRecentDB();

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
                        //insert DB
                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        dbHelper.insert(
                                plain_text1.getText().toString(),
                                "",
                                tag_name.getText().toString(),
                                currentTime,
                                0,
                                ""
                        );

                        //insert UI
                        Arritem item = new Arritem();
                        item.setTitle(plain_text1.getText().toString());
                        item.setTag(tag_name.getText().toString());
                        //날짜 표기하는 것은 기술적 문제로 구현을 못하겠습니다.
                        mAdapter.addItem(item);

                        mRv_mapthings.smoothScrollToPosition(0);
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "할일 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.show();
            }
        });
    }

    private void loadRecentDB() {
        //저장된 DB 가져오기
        mAlist = dbHelper.getAlist();
        if(mAdapter == null)    {
            mAdapter = new CustomAdapter(mAlist, this, dbHelper);
            mRv_mapthings.setHasFixedSize(true);
            mRv_mapthings.setAdapter(mAdapter);
        }
    }
}
