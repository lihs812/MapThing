package com.example.mapthing;

import android.app.Dialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Arritem> mAlist; // 게시글 아이템을 담은 리스트
    private Context mContext; // 어댑터를 사용하는 컨텍스트
    private DBHelper mGETDB; // 데이터베이스 관리를 위한 DBHelper 객체

    public CustomAdapter(ArrayList<Arritem> mAlist, Context mContext, DBHelper mGETDB) {
        this.mAlist = mAlist;
        this.mContext = mContext;
        this.mGETDB = mGETDB; // mGETDB 초기화
    }


    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ViewHolder 객체를 생성하여 반환하는 메서드
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // ViewHolder와 데이터를 바인딩하는 메서드
        holder.custom_title.setText(mAlist.get(position).getTitle()); // 게시글 제목 설정
        holder.write_date.setText(mAlist.get(position).getWriteDate()); // 작성일 설정
    }

    @Override
    public int getItemCount() {
        // 아이템 개수 반환
        return mAlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // 아이템 뷰를 담는 ViewHolder 클래스

        private TextView custom_title; // 게시글 제목을 표시하는 TextView
        private TextView write_date; // 작성일을 표시하는 TextView

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            custom_title = itemView.findViewById(R.id.custom_title);
            write_date = itemView.findViewById(R.id.write_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int curpos = getAdapterPosition(); // 현재 클릭한 아이템 위치
                    Arritem arritem = mAlist.get(curpos);

                    String[] strChoiceItems = {"수정하기", "삭제하기"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("원하는 작업을 선택해주세요");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            if (position == 0) {
                                // 수정하기
                                // 팝업창
                                Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                                dialog.setContentView(R.layout.popup_edit);
                                EditText plain_text1 = dialog.findViewById(R.id.plain_text1);
                                EditText tag_name = dialog.findViewById(R.id.tag_name);
                                Button btn_ok = dialog.findViewById(R.id.btn_ok);

                                btn_ok.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // 테이블 업데이트
                                        String title = plain_text1.getText().toString();
                                        String tag = tag_name.getText().toString();
                                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); // 현재 시간 받아오기
                                        String beforeTime = arritem.getWriteDate();
                                        int itemId = arritem.getId(); // 예시: Arritem 객체에서 _id 값을 가져옴

                                        // DBHelper를 사용하여 테이블 업데이트
                                        mGETDB.update(
                                                title,
                                                "",
                                                tag,
                                                beforeTime,
                                                0,
                                                "alarmTime"
                                        );

                                        // UI 업데이트
                                        arritem.setTitle(title);
                                        arritem.setTag(tag);
                                        arritem.setWriteDate(currentTime);
                                    }
                                });
                                dialog.show();

                            } else if (position == 1) {
                                // 테이블 삭제
                                String beforeTime = arritem.getWriteDate();
                                mGETDB.delete(beforeTime);

                                // UI 삭제
                                mAlist.remove(curpos);
                                notifyItemRemoved(curpos);
                                Toast.makeText(mContext,"목록이 제거되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.show();
                }
            });
        }
    }

    // 현재 어댑터에 새로운 게시글 아이템을 전달받아 추가함
    public void addItem(Arritem _item)  {
        mAlist.add(0, _item);
        notifyItemInserted(0); // 새로고침
    }
}
