package com.djl.forgetpwd;

import com.djl.forgetpwd.bean.NameValuePairBean;
import com.djl.forgetpwd.view.DJLEncrypt;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        byte a = (byte) 0xF4;
        byte b = (byte) 0x7f;
        System.out.println(a);
        byte[] hash = null;
        try {
            String pwd = "djl";
            hash = MessageDigest.getInstance("MD5").digest(pwd.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            // throw new KJException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            // throw new KJException("Huh, UTF-8 should be supported?", e);
        }
        /**
         * 去负数
         */
        byte md5_15[] = new byte[15];
        for (int i = 0; i < md5_15.length; i++) {
            md5_15[i] = (byte) (hash[i] & 0x7f);
        }
        String data = "hehweh 234213412312341234sdjsadjsd";
        byte[] bytes = data.getBytes(Charset.forName("Utf-8"));
        int length = bytes.length;
        int encryptGroupCount = length / 15 + 1;
        bytes = Arrays.copyOf(bytes, encryptGroupCount * 15);
        System.out.println(Arrays.toString(bytes));
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (md5_15[i % 15] ^ bytes[i]);
        }
        System.out.println(Arrays.toString(bytes));
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (md5_15[i % 15] ^ bytes[i]);
        }
        System.out.println(Arrays.toString(bytes));
        System.out.println(1 ^ 3);
        System.out.println(2 & 3);
        System.out.println(a & b);
        assertEquals(4, 2 + 2);
    }

    @Test
    public void DJLEncryptTest() {
        int times = 3;
        DJLEncrypt djlEncrypt = new DJLEncrypt();
        String pwd = "二笔";
        String encrypt = djlEncrypt.encryptString("段金磊是神  哈哈哈哈 sdfwewerwerwer  hahahaha    erererer", pwd, times);
        System.out.println(encrypt);
        String decrypt = djlEncrypt.decryptString(encrypt, pwd, times);
        System.out.println(decrypt);
    }

    @Test
    public void alarm() {
        String data = "aaaaaa bbbb c\nddd eeee  fff\n ggg hhh iii ";
        System.out.println(data);
//        String[] rows = data.split("\n");
//        ArrayList<AlarmBean> been = new ArrayList<>();
//        for (String row : rows) {
//            AlarmBean bean = AlarmBean.parse(row);
//            if (bean != null) {
//                been.add(bean);
//            }
//        }
//        for (AlarmBean alarmBean : been) {
//            System.out.println(alarmBean.toString());
//        }
        SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        try {
            Date parse = dateFormat3.parse("3时");
            System.out.println(parse.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
//        AlarmBean.parses(data);
    }


}