package com.example.easyeat.slide;

public class ScreenItem {

    String title;
    int description;
    int screenImg;

    public ScreenItem(String title, int description, int screenImg) {
        this.title = title;
        this.description = description;
        this.screenImg = screenImg;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setScreenImg(int screenImg) {
        this.screenImg = screenImg;
    }

    public String getTitle() {
        return title;
    }

    public int getScreenImg() {
        return screenImg;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }
}
