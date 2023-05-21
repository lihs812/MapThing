package com.example.mapthing;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    // Test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewCapture); //listview 선언

        CustomCaptureAdapter customCaptureAdapter = new CustomCaptureAdapter(MainActivity.this); //CustomAdapter
        listView.setAdapter(customCaptureAdapter); //ListView에 Adapter 연결

        customCaptureAdapter.addItem("물건 1","001"); //Item 추가
        customCaptureAdapter.addItem("물건 2","002");
        customCaptureAdapter.addItem("물건 3","003");
        customCaptureAdapter.addItem("물건 4","004");
        customCaptureAdapter.addItem("물건 5","005");
        customCaptureAdapter.addItem("물건 6","006");
        customCaptureAdapter.addItem("물건 7","007");

        customCaptureAdapter.notifyDataSetChanged(); //Adapter 변경시 적용
    }

}
