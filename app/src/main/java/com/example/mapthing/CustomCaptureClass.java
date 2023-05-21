package com.example.mapthing;

import android.graphics.Bitmap;

public class CustomCaptureClass {


    private String customTitle; // 이름
    private String realPath; // 실제 경로
    private String category; // 분류
    private Bitmap capture; // 이미지 파일
    private boolean status; // 상태
    private String tag; // tag

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

    public boolean getStatus() {
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

    @Override
    public String toString() {
        return "CustomCaptureClass [customTitle: " + customTitle
                + ", category: " + category
                + ", realPath: " + realPath
                + ", capture: " + capture
                + ", status: " + status
                + ", tag: " + tag + "]";
    }

}
