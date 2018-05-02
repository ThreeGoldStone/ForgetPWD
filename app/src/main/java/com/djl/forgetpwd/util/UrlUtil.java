package com.djl.forgetpwd.util;

import java.io.UnsupportedEncodingException;

/**
 * url转码、解码
 *
 * @author lifq
 * @date 2015-3-17 下午04:09:35
 */
public class UrlUtil {
    private final static String ENCODE = "utf-8";

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
            System.out.println("getURLDecoderString  >>  " + str);
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
//        %E6%AE%B5%E9%87%91%E7%A3%8A%E6%98%AF%E7%A5%9E++%E5%93%88%E5%93%88%E5%93%88%E5%93%88++hahahaha++++erererer
//        %E6%AE%B5%E9%87%91%E7%A3%8A%E6%98%AF%E7%A5%9E++%E5%93%88%E5%93%88%E5%93%88%E5%93%88++hahahaha++++ererererBo{BMj<eDMLn(m2vs1Bg'{Jl:hlEYH/6u9vr]A'Fl]${^%hkKgs]w-@ucGSSYyH?YA
//            IsrDt#H\I$nm~t-1S!���
        try {
            System.out.println("getURLEncoderString  >>  " + str);
            result = java.net.URLEncoder.encode(str, ENCODE);
            System.out.println("getURLEncoderString result >>  " + result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


}