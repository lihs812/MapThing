package com.example.mapthing;

import android.graphics.Bitmap;

public class Arritem {
    private int id; //고유 ID (물건을 등록할 때마다 1 증가)
    private String customTitle; //물건 이름
    private String realPath; // 실제 경로
    private String category; // 분류
    private Bitmap capture; // 이미지 파일
    private boolean status; // 상태
    private String tag; // tag

    public Arritem() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomTitle() {
        return customTitle;
    }

    public void setCustomTitle(String customTitle) {
        this.customTitle = customTitle;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Bitmap getCapture() {
        return capture;
    }

    public void setCapture(Bitmap capture) {
        this.capture = capture;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getWriteDate() {
        return getWriteDate();
    }
}
