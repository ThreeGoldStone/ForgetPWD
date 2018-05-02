package com.djl.forgetpwd.view;

/**
 * Created by DJl on 2017/8/29.
 * email:1554068430@qq.com
 */

public interface IPWDView {
    /**
     * 设置需要转化成像素的内容
     */
    void setContent(String content, String pwd);

    /**
     * 获取像素点的内容
     */
    String getContent(String pwd) throws Exception;

    /**
     * 加载图片
     */
    void loadImage(String path);

    /**
     * 保存图片
     */
    void saveImage(String path);
}
