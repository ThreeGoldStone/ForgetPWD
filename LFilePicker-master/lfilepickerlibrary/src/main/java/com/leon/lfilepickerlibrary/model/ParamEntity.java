package com.leon.lfilepickerlibrary.model;

import android.os.Environment;

import com.leon.lfilepickerlibrary.utils.StringUtils;

import java.io.Serializable;

/**
 * 作者：Leon
 * 时间：2017/3/21 14:50
 */
public class ParamEntity implements Serializable {
    private String title;
    private String titleColor;
    private String backgroundColor;
    private int backIcon;
    private boolean mutilyMode;
    private String addText;
    private int iconStyle;
    private String[] fileTypes;
    private String notFoundFiles;
    private boolean isDir;
    private String rootPath;

    public boolean isDir() {
        return isDir;
    }

    public ParamEntity setDir(boolean dir) {
        isDir = dir;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isMutilyMode() {
        return mutilyMode;
    }

    public void setMutilyMode(boolean mutilyMode) {
        this.mutilyMode = mutilyMode;
    }

    public int getBackIcon() {
        return backIcon;
    }

    public void setBackIcon(int backIcon) {
        this.backIcon = backIcon;
    }

    public String getAddText() {
        return addText;
    }

    public void setAddText(String addText) {
        this.addText = addText;
    }

    public int getIconStyle() {
        return iconStyle;
    }

    public void setIconStyle(int iconStyle) {
        this.iconStyle = iconStyle;
    }

    public String[] getFileTypes() {
        return fileTypes;
    }

    public void setFileTypes(String[] fileTypes) {
        this.fileTypes = fileTypes;
    }

    public String getNotFoundFiles() {
        return notFoundFiles;
    }

    public void setNotFoundFiles(String notFoundFiles) {
        this.notFoundFiles = notFoundFiles;
    }

    public String getRootPath() {
        if (StringUtils.isEmpty(rootPath))
            rootPath =  Environment.getExternalStorageDirectory().getAbsolutePath();
        return rootPath;
    }

    public ParamEntity setRootPath(String rootPath) {
        this.rootPath = rootPath;
        return this;
    }
}
