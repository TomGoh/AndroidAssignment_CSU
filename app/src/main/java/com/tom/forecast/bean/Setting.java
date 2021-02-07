package com.tom.forecast.bean;

import java.io.Serializable;
import java.util.List;

public class Setting implements Serializable {
    String title;
    String detail;
    boolean checkable;
    boolean checked;

    public Setting(String title, boolean checkable) {
        this.title = title;
        this.checkable = checkable;
        this.checked=false;
        this.detail="";
    }

    public Setting(String title, String detail, boolean checkable) {
        this.title = title;
        this.detail = detail;
        this.checkable = checkable;
    }

    public Setting(String title, boolean checkable, boolean checked) {
        this.title = title;
        this.checkable = checkable;
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isCheckable() {
        return checkable;
    }

    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void update(String detail){
        this.detail=detail;
    }

    public void check(boolean checked){
        if(checkable){
            this.checked=checked;
        }
    }
}
