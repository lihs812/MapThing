package com.example.mapthing;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRv_mapthings;
    private FloatingActionButton mBtn_write;
    private ArrayList<Arritem> mAlist;
    private DBHelper mGETDB;
    private Context mContext; // mContext 변수 추가
    private CustomAdapter mAdapter;
    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private ArrayList<String> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_main);
        mContext = this;
        setInit();
    }

    private void setInit() {
        // DBHelper 인스턴스 생성
        mGETDB = new DBHelper(mContext, 2);

        // RecyclerView 및 FloatingActionButton 요소 초기화
        mRv_mapthings = findViewById(R.id.rv_list);
        mBtn_write = findViewById(R.id.floatingActionButton4);
        mAlist = new ArrayList<>();

        // 데이터베이스에서 최신 데이터 가져오기
        loadRecentDB();

        // FloatingActionButton 클릭 리스너
        mBtn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 팝업창
                Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.popup_edit);
                EditText plain_text1 = dialog.findViewById(R.id.plain_text1);
                EditText tag_name = dialog.findViewById(R.id.tag_name);
                EditText path_name = dialog.findViewById(R.id.path_name);
                Button btn_ok = dialog.findViewById(R.id.btn_ok);

//                void list_db_set(){
//
//                }ㅅ

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // DB에 데이터 삽입
                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        mGETDB.insert(
                                plain_text1.getText().toString(),
                                path_name.getText().toString(),
                                tag_name.getText().toString(),
                                currentTime,
                                0,
                                ""
                        );

                        // UI에 아이템 삽입
                        Arritem item = new Arritem();
                        item.setTitle(plain_text1.getText().toString());
                        item.setTag(tag_name.getText().toString());
                        //item.setTag(path_name.getText().toString());

                        mAdapter.addItem(item);

                        mRv_mapthings.smoothScrollToPosition(0);
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "물건이 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });

        //tag 화면전환
        Button tag_button = findViewById(R.id.tag_button);
        tag_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), tagActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadRecentDB() {
        // 저장된 DB 가져오기
        mAlist = mGETDB.getAlist();
        if (mAdapter == null) {
            mAdapter = new CustomAdapter(mAlist, this, mGETDB);
            mRv_mapthings.setHasFixedSize(true);
            mRv_mapthings.setAdapter(mAdapter);
        } else {
            mAdapter.updateData(mAlist);
        }
    }
}
