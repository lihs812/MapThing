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

    private String formatDateString(String fullDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inputFormat.parse(fullDate);
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return fullDate;
        }
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Arritem item = mAlist.get(position);
        holder.custom_title.setText(item.getTitle());
        holder.tag_name.setText(item.getTag());
        holder.write_date.setText(formatDateString(item.getWriteDate()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.update_or_del_popup);
                EditText plain_text1 = dialog.findViewById(R.id.plain_text1);
                EditText tag_name = dialog.findViewById(R.id.tag_name);
                EditText path_name = dialog.findViewById(R.id.path_name);
                Button btn_update = dialog.findViewById(R.id.btn_update);
                Button btn_delete = dialog.findViewById(R.id.btn_del);

                plain_text1.setText(item.getTitle());
                tag_name.setText(item.getTag());
                path_name.setText(item.getPath());

                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newTitle = plain_text1.getText().toString();
                        if (isDuplicateName(newTitle)) {
                            Toast.makeText(mContext, "이미 존재하는 이름입니다. 다른 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                        mGETDB.Update(
                                newTitle,
                                path_name.getText().toString(),
                                tag_name.getText().toString(),
                                currentTime,
                                0,
                                ""
                        );

                        item.setTitle(newTitle);
                        item.setTag(tag_name.getText().toString());
                        item.setWriteDate(currentTime);
                        item.setPath(path_name.getText().toString());
                        notifyDataSetChanged();

                        dialog.dismiss();
                        Toast.makeText(mContext, "물건 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGETDB.Delete(item.getTitle());
                        int adapterPosition = holder.getAdapterPosition();
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            mAlist.remove(adapterPosition);
                            notifyItemRemoved(adapterPosition);
                        }
                        dialog.dismiss();
                        Toast.makeText(mContext, "물건 정보가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        notifyDataSetChanged();
                    }
                });

                dialog.show();
            }
        });
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
        }
    }

    private boolean isDuplicateName(String newName) {
        for (Arritem item : mAlist) {
            if (item.getTitle().equals(newName)) {
                return true;
            }
        }
        return false;
    }

    public void addItem(Arritem _item) {
        mAlist.add(0, _item);
        notifyItemInserted(0);
    }
}
