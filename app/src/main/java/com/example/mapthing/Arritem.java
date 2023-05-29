package com.example.mapthing;

import android.graphics.Bitmap;

public class Arritem {

    private int ID;             //생성시 1 씩 증가
    private String title;       //물건 이름
    private String path;        // 상위 경로
    private String tag;         // 태그
    private String writeDate;   // 생성일자
    private int alarmType;      // 알람종류(0 사용안함, 1 날짜기준 까지, 2 일기준 까지, 3 일 마다)
    private String alarmTime;   // 알람시간
    private Bitmap capture;     // 이미지 파일

    public Arritem() {

    }
    public int getId() {
        return ID;
    }

    public void setId(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Bitmap getCapture() {
        return capture;
    }

    public void setCapture(Bitmap capture) {
        this.capture = capture;
    }

}
