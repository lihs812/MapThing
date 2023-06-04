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
    public void updateData(ArrayList<Arritem> data) {
        mAlist.clear();
        mAlist.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Arritem item = mAlist.get(position);
        holder.custom_title.setText(item.getTitle());
        holder.tag_name.setText(item.getTag());
        holder.write_date.setText(item.getWriteDate());
    }

    @Override
    public int getItemCount() {
        return mAlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView custom_title;
        private TextView tag_name;
        private TextView write_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            custom_title = itemView.findViewById(R.id.custom_title);
            tag_name = itemView.findViewById(R.id.tag_name);
            write_date = itemView.findViewById(R.id.write_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                // 수정된 CustomAdapter 클래스의 ViewHolder 내의 onClick 메서드
                @Override
                public void onClick(View v) {
                    int curpos = getAdapterPosition();  // 현재 리스트 클릭한 아이템 위치
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
                                        // DB에 데이터 수정
                                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                        mGETDB.Update(
                                                plain_text1.getText().toString(),
                                                "",
                                                tag_name.getText().toString(),
                                                currentTime,
                                                0,
                                                ""
                                        );

                                        // UI에 아이템 수정
                                        arritem.setTitle(plain_text1.getText().toString());
                                        arritem.setTag(tag_name.getText().toString());
                                        arritem.setWriteDate(currentTime);
                                        notifyDataSetChanged();

                                        dialog.dismiss();
                                        Toast.makeText(mContext, "물건 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dialog.show();

                            } else if (position == 1) {
                                // 삭제하기
                                // DB에서 데이터 삭제
                                mGETDB.Delete(arritem.getTitle());

                                // UI에서 아이템 삭제
                                mAlist.remove(curpos);
                                notifyItemRemoved(curpos);
                                Toast.makeText(mContext, "물건 정보가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
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
        notifyItemInserted(0); //새로고침
    }
}
