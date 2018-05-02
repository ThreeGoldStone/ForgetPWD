package com.djl.forgetpwd.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by DJl on 2018/1/2.
 * email:1554068430@qq.com
 */

public class NameValuePairBean implements Serializable {
    private String name;
    private String value;


    private int progress;
    private ArrayList<NameValuePairBean> subs=new ArrayList<>();

    public int getProgress() {
        return progress;
    }

        public NameValuePairBean setProgress(int progress) {
        this.progress = progress;
        return this;
    }

    public NameValuePairBean setSubs(ArrayList<NameValuePairBean> subs) {
        this.subs = subs;
        return this;
    }


    public NameValuePairBean setSubs(NameValuePairBean[] nameValuePairBeen) {
        subs = new ArrayList<>(Arrays.asList(nameValuePairBeen));
        return this;

    }
 public ArrayList<NameValuePairBean> getSubs() {
        return subs;
}
    public String getName() {
        return name;
    }

    public NameValuePairBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public NameValuePairBean setValue(String value) {
        this.value = value;
        return this;
    }


}
