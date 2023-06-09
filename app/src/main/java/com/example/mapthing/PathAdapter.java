package com.example.mapthing;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PathAdapter extends RecyclerView.Adapter<PathAdapter.ViewHolder> {
    private List<String> pathList;

    public PathAdapter(List<String> pathList) {
        this.pathList = pathList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // item_path.xml 레이아웃을 inflate하여 ViewHolder를 생성하고 반환합니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_path, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // ViewHolder의 데이터를 설정합니다.
        String path = pathList.get(position);
        holder.bind(path);
    }

    @Override
    public int getItemCount() {
        return pathList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView pathTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //뷰 초기화
            pathTextView = itemView.findViewById(R.id.rv_list);
        }

        public void bind(String path) {
            // 데이터를 뷰에 표시
            pathTextView.setText(path);
        }
    }
}
