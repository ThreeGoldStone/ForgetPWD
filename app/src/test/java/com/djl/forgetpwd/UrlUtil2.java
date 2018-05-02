package com.djl.forgetpwd;

import com.djl.forgetpwd.bean.NameValuePairBean;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * url转码、解码
 *
 * @author lifq
 * @date 2015-3-17 下午04:09:35
 */
public class UrlUtil2 {
    private final static String ENCODE = "gbk";

    /**
     * URL 解码
     *
     * @return String
     * @author lifq
     * @date 2015-3-17 下午04:09:51
     */
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * URL 转码
     *
     * @return String
     * @author lifq
     * @date 2015-3-17 下午04:10:28
     */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @return void
     * @author lifq
     * @date 2015-3-17 下午04:09:16
     */
    public static void main(String[] args) {
        String str = "测试1";
        System.out.println(getURLEncoderString(str));
        System.out.println(getURLDecoderString(str));

    }

    @Test
    public void test() {
        String str = "测试1";
        System.out.println(getURLEncoderString(str));
        System.out.println(getURLDecoderString(str));
    }
//    @Test
//    public void nameValuePairTest() {
//        NameValuePairBean base = new NameValuePairBean();
//        NameValuePairBean[] pairBeen = {
//                new NameValuePairBean().setName("sub1Name").setValue("sub1Value"),
//                new NameValuePairBean().setName("sub2Name").setValue("sub2Value"),
//                new NameValuePairBean().setName("sub3Name").setValue("sub3Value").setSubs(new NameValuePairBean[]{
//                        new NameValuePairBean().setName("subsub1Name").setValue("subsub1Value"),
//                        new NameValuePairBean().setName("subsub2Name").setValue("subsub2Value"),
//                }),
//        };
//        base.setName("baseName").setValue("baseValue").setSubs(pairBeen);
//        System.out.println(base);
//    }
}