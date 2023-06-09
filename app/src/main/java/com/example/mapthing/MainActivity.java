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
    private Context mContext;
    private CustomAdapter mAdapter;
    private List<String> list;
    private ListView listView;
    private EditText editSearch;
    private ArrayList<String> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_main);
        mContext = this;
        setInit();
    }

    private void setInit() {
        mGETDB = new DBHelper(mContext, 2);
        mRv_mapthings = findViewById(R.id.rv_list);
        mBtn_write = findViewById(R.id.floatingActionButton4);
        mAlist = new ArrayList<>();

        loadRecentDB();

        mBtn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.popup_edit);
                EditText plain_text1 = dialog.findViewById(R.id.plain_text1);
                EditText tag_name = dialog.findViewById(R.id.tag_name);
                EditText path_name = dialog.findViewById(R.id.path_name);
                Button btn_ok = dialog.findViewById(R.id.btn_update);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String itemName = plain_text1.getText().toString();
                        String path = path_name.getText().toString();

                        // 이미 동일한 이름의 객체가 있는지 확인
                        boolean isDuplicate = isDuplicateObjectName(itemName);

                        // 경로를 입력하지 않은 경우, 빈 문자열로 설정
                        if (path.isEmpty()) {
                            path = "";
                        }
                        //동일한 이름을 가진 개체가 없고, 경로를 이미 있는 객체들중에서 고르거나, 패스가 비어있을떄
                        if (!isDuplicate && (isDuplicateObjectName(path) || path.isEmpty())) {
                            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            mGETDB.insert(
                                    itemName,
                                    path,
                                    tag_name.getText().toString(),
                                    currentTime,
                                    0,
                                    ""
                            );

                            Arritem item = new Arritem();
                            item.setTitle(itemName);
                            item.setTag(tag_name.getText().toString());

                            mAdapter.addItem(item);

                            mRv_mapthings.smoothScrollToPosition(0);
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "물건이 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                            System.out.println(mGETDB.getPath(itemName).toString());
                        }

                        // 이미 동일한 이름의 객체가 있는 경우 메시지 출력
                        if (isDuplicate) {
                            Toast.makeText(MainActivity.this, "동일한 이름의 객체가 이미 존재합니다.", Toast.LENGTH_SHORT).show();
                        }
                        else if (!isDuplicateObjectName(path)){
                            Toast.makeText(MainActivity.this, "경로가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                dialog.show();
            }
        });

        Button tag_button = findViewById(R.id.tag_button);
        tag_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), tagActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadRecentDB() {
        mAlist = mGETDB.getAlist();
        if (mAdapter == null) {
            mAdapter = new CustomAdapter(mAlist, this, mGETDB);
            mRv_mapthings.setHasFixedSize(true);
            mRv_mapthings.setAdapter(mAdapter);
        } else {
            mAdapter.updateData(mAlist);
        }
    }

    private boolean isDuplicateObjectName(String objectName) {
        ArrayList<Arritem> itemList = mGETDB.getAlist();
        for (Arritem item : itemList) {
            if (item.getTitle().equals(objectName)) {
                return true;
            }
        }
        return false;
    }
}
