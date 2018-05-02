package com.djl.netUtils;

/**
 * Created by Administrator on 2016/3/16.
 */
public class SimpleTag {
    public String code;
    /**
     * 解析body的类型
     */
    public ParseType type;
    public Object others;

    public static enum ParseType {
        string_default
    }

}
