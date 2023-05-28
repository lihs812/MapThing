package com.example.mapthing;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRv_mapthings;
    private FloatingActionButton mBtn_write;
    private ArrayList<Arritem> mGetAlist;
    private GETDB mGETDB;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();
    }

    private void setInit() {
        mGETDB = new GETDB(this);
        mRv_mapthings = findViewById(R.id.rv_list);
        mBtn_write = findViewById(R.id.floatingActionButton4);
        mGetAlist = new ArrayList<>();


        /*
        mBtn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //팝업창
                Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.popup_edit);
                EditText plain_text1 = dialog.findViewById(R.id.plain_text1);
                Button btn_ok = dialog.findViewById(R.id.btn_ok);


                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                        mGETDB.InsertDATA(plain_text1.getText(),toString(),.getText().toString);
                    }
                });
            }
        });
        */
    }

}
