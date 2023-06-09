package com.example.mapthing;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class path_view_list extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<String> pathList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_view_main);

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 경로 데이터 가져오기
        DBHelper dbHelper = new DBHelper(this, 2); // 버전 번호를 2로 변경
        pathList = dbHelper.getPath("물건의_이름");

        // 어댑터 설정
        adapter = new PathAdapter(pathList);
        recyclerView.setAdapter(adapter);
    }
}
