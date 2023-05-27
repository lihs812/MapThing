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

        customCaptureAdapter.addItem("거실","001"); //Item 추가
        customCaptureAdapter.addItem("안방","002");
        customCaptureAdapter.addItem("침실","003");
        customCaptureAdapter.addItem("창고","004");
        customCaptureAdapter.addItem("주방","005");



        customCaptureAdapter.notifyDataSetChanged(); //Adapter 변경시 적용
    }

}
