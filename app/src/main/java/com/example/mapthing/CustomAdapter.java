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

    private ArrayList<Arritem> mAlist;
    private Context mContext;
    private DBHelper mGETDB;

    public CustomAdapter(ArrayList<Arritem> mAlist, Context mContext, DBHelper mGETDB) {
        this.mAlist = mAlist;
        this.mContext = mContext;
        this.mGETDB = mGETDB;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.custom_title.setText(mAlist.get(position).getTitle());
        holder.write_date.setText(mAlist.get(position).getWriteDate());
    }

    @Override
    public int getItemCount() {
        return mAlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView custom_title;
        private TextView write_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            custom_title = itemView.findViewById(R.id.custom_title);
            write_date = itemView.findViewById(R.id.write_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int curpos = getAdapterPosition();  //현재 리스트 클릭한 아이템 위치
                    Arritem arritem = mAlist.get(curpos);

                    String[] strChoiceItems = {"수정하기", "삭제하기"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("원하는 작업을 선택해주세요");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            if (position == 0) {
                                //수정하기
                                //팝업창
                                Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                                dialog.setContentView(R.layout.popup_edit);
                                EditText plain_text1 = dialog.findViewById(R.id.plain_text1);
                                EditText tag_name = dialog.findViewById(R.id.tag_name);
                                Button btn_ok = dialog.findViewById(R.id.btn_ok);

                                btn_ok.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        //update table
                                        String title = plain_text1.getText().toString();
                                        String tag = tag_name.getText().toString();
                                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());       //현재 시간 받아오기
                                        String beforeTime = arritem.getWriteDate();
                                        int itemId = arritem.getId(); // 예시: Arritem 객체에서 _id 값을 가져옴


                                        mGETDB.Update(
                                                title,
                                                "",
                                                tag,
                                                beforeTime,
                                                0,
                                                "alarmTime"
                                        );

                                        //UI
                                        arritem.setTitle(title);
                                        arritem.setTag(tag);
                                        arritem.setWriteDate(currentTime);

                                    }
                                });
                                dialog.show();

                            } else if (position == 1) {
                                //del table
                                String beforeTime =  arritem.getWriteDate();
                                mGETDB.Delete(beforeTime);

                                //del UI
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

    //현재 어댑터에 새로운 게시글 아이템을 전달받아 추가 함
    public void addItem(Arritem _item)  {
        mAlist.add(0, _item);
        notifyItemInserted(0 ); //새로고침
    }
}
