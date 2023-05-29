package com.example.mapthing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Arritem> mAlist;
    private Context mContext;
    private GETDB mGETDB;

    public CustomAdapter(ArrayList<Arritem> mAlist, Context mContext, GETDB mGETDB) {
        this.mAlist = mAlist;
        this.mContext = mContext;
        mGETDB = new GETDB(mContext);
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.custom_title.setText(mContext.getString(position).toString());
        holder.write_date.setText(mContext.getString(position).toString());
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
                            } else if (position == 1) {
                                //삭제하기
                            }
                        }
                    });
                    builder.show();
                }
            });
        }
    }
}
