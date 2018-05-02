package com.djl.forgetpwd.bean;

import com.djl.forgetpwd.util.utils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by DJl on 2018/2/10.
 * email:1554068430@qq.com
 */

public class PictureContentBean {
    //    创建日期
    @SerializedName("1")
    private String createTime;
    //    上次保存时间
    @SerializedName("2")
    private String lastSaveTime;
    //    上次打开的环境信息
    @SerializedName("3")
    private String lastOpenPhoneInfo;
    //    数据
    @SerializedName("4")
    private ArrayList<PictureContentBean> beans = new ArrayList<>();
    //    标题
    @SerializedName("5")
    private String title;
    //    内容
    @SerializedName("6")
    private String content;
    @SerializedName("7")
    private boolean isEdit;
    @SerializedName("8")
    private String temporaryContent;

    public String getCreateTime() {
        return createTime;
    }

    public PictureContentBean() {
        this.createTime = utils.getTime3();
    }

    public PictureContentBean setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getLastSaveTime() {
        return lastSaveTime;
    }

    public PictureContentBean setLastSaveTime(String lastSaveTime) {
        this.lastSaveTime = lastSaveTime;
        return this;
    }

    public String getLastOpenPhoneInfo() {
        return lastOpenPhoneInfo;
    }

    public PictureContentBean setLastOpenPhoneInfo(String lastOpenPhoneInfo) {
        this.lastOpenPhoneInfo = lastOpenPhoneInfo;
        return this;
    }

    public ArrayList<PictureContentBean> getBeans() {
        return beans;
    }

    public PictureContentBean setBeans(ArrayList<PictureContentBean> beans) {
        this.beans = beans;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PictureContentBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PictureContentBean setContent(String content) {
        this.content = content;
        return this;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public PictureContentBean setEdit(boolean edit) {
        isEdit = edit;
        return this;
    }

    public String getTemporaryContent() {
        return temporaryContent;
    }

    public PictureContentBean setTemporaryContent(String temporaryContent) {
        this.temporaryContent = temporaryContent;
        return this;
    }



}
