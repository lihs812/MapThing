package com.example.mapthing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomCaptureAdapter extends BaseAdapter{

    private static final String TAG = "CustomCustomCaptureClass";

    private ArrayList<CustomCaptureClass> captureItems = new ArrayList<>();
    private ViewHolder mViewHolder;
    private Context mContext;

    public CustomCaptureAdapter(Context context) {
        this.mContext = context;
    }

    public class ViewHolder {
        private ImageView ivCapture;

        private TextView titleTextView;
        private TextView categoryTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_custom_capture_item, parent, false);

            mViewHolder = new ViewHolder();

            mViewHolder.ivCapture = convertView.findViewById(R.id.iv_photo_item);

            mViewHolder.titleTextView = convertView.findViewById(R.id.tv_item_title);
            mViewHolder.categoryTextView = convertView.findViewById(R.id.tv_item_category);

            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        CustomCaptureClass captureItem = captureItems.get(position);

        //Data 세팅
        mViewHolder.ivCapture.setImageBitmap(captureItem.getCapture());

        mViewHolder.titleTextView.setText(captureItem.getCustomTitle());
        mViewHolder.categoryTextView.setText(captureItem.getCategory());

        return convertView;
    }

    @Override
    public int getCount() {
        return captureItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return captureItems.get(position);
    }

    public void addItem(String strTitle, String strCategory) {
        CustomCaptureClass item = new CustomCaptureClass();

        item.setCustomTitle(strTitle);
        item.setCategory(strCategory);
        item.setStatus(false);

        captureItems.add(item);
    }

}

