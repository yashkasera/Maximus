package com.craft404.maximus.model;

import android.graphics.Bitmap;

import java.io.File;
import java.io.Serializable;

public class Status implements Serializable {
    private static File file;
    String title, path;
    private Bitmap thumbnail;
    private boolean isVideo;

    public Status(File file, String title, String path) {
        Status.file = file;
        this.title = title;
        this.path = path;
        this.isVideo = file.getName().endsWith(".mp4");
    }

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        com.craft404.maximus.model.Status.file = file;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
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

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }
}
