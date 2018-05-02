package com.djl.forgetpwd.view;

import com.djl.forgetpwd.util.UrlUtil;
import com.djl.javaUtils.CipherUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by DJl on 2017/8/29.
 * email:1554068430@qq.com
 */

public class DJLEncrypt {
    private String staticPWD = "djl";

    /**
     * 加密文本
     *
     * @param content 被加密文本
     * @param pwd     加密秘吗
     * @param times   加密次数
     * @return 加密后字符串
     * @
     */
    public String encryptString(String content, String pwd, int times) {
        byte[] encrypt = encrypt(content, pwd, times);
        return new String(encrypt);
    }

    /**
     * 加密文本
     *
     * @param content 被加密文本
     * @param pwd     加密秘吗
     * @param times   加密次数
     * @return 加密后字符数组
     * @
     */
    public byte[] encrypt(String content, String pwd, int times) {
        //汉字转码
        times = Math.max(1, times);
        content = UrlUtil.getURLEncoderString(content);
        byte[] bytes = content.getBytes(Charset.forName("Utf-8"));
        long lengthBefore = bytes.length;
        bytes = encrypt(bytes, pwd, times);
        long lengthAfter = bytes.length;
        byte b = (byte) (lengthAfter - lengthBefore);

        //加密数据的最后一位表示15位补齐后的差异位数
        if (b != 0) {
            bytes = Arrays.copyOf(bytes, bytes.length + 1);
            bytes[bytes.length - 1] = b;
        }

        return bytes;
    }

    /**
     * @param bytes 加密原数组
     * @param pwd   加密密码
     * @return 返回加密后数组
     * @
     */
    public byte[] encrypt(byte[] bytes, String pwd, int times) {
        byte[] encrypt = bytes;
        for (int i = 0; i < times; i++) {
            byte[] md5_15 = getMD5_15(pwd, i + 1);
            encrypt = encrypt(encrypt, md5_15);
        }
        return encrypt;
    }

    private byte[] encrypt(byte[] bytes, byte[] md5_15) {
        int length = bytes.length;
        // 如果数组不是15位对齐 就按照15位对齐
        int _15 = 15 - length % 15;
        Random random = new Random();
        if (_15 != 0 && _15 != 15) {
            int encryptGroupCount = length / 15 + 1;
            bytes = Arrays.copyOf(bytes, encryptGroupCount * 15);
            int lengthAfter = bytes.length;
            for (int i = 0; i < _15; i++) {
                bytes[lengthAfter - 1 - i] = (byte) random.nextInt(127);
            }
        }
        System.out.println(Arrays.toString(bytes));
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (md5_15[i % 15] ^ bytes[i]);
        }
        return bytes;
    }

    public String decryptString(String content, String pwd, int times) {
        byte[] bytes = content.getBytes(Charset.forName("Utf-8"));
//        //取出并去除数据中的差异个数位
//        int lengthAdd = 0;
//        if (bytes.length % 15 == 1) {
//            lengthAdd = bytes[bytes.length - 1];
//            bytes = Arrays.copyOfRange(bytes, 0, bytes.length - 1);
//        }
//        byte[] decrypt = decrypt(bytes, pwd);
//        String encoded = new String(decrypt, 0, bytes.length - lengthAdd);
//        return UrlUtil.getURLDecoderString(encoded);
        return decryptString(bytes, pwd, times);
    }

    public String decryptString(byte[] bytes, String pwd, int times) {
        //取出并去除数据中的差异个数位
        int lengthAdd = 0;
        if (bytes.length % 15 == 1) {
            lengthAdd = bytes[bytes.length - 1];
            bytes = Arrays.copyOfRange(bytes, 0, bytes.length - 1);
        }
        byte[] decrypt = decrypt(bytes, pwd, times);
        String encoded = new String(decrypt, 0, bytes.length - lengthAdd);
        return UrlUtil.getURLDecoderString(encoded);
    }

    private byte[] decrypt(byte[] bytes, String pwd, int times) {
        byte[] decrypt = bytes;
        for (int i = times; i > 0; i--) {
            byte[] md5_15 = getMD5_15(pwd, i);
            decrypt = encrypt(decrypt, md5_15);
        }
        return decrypt;
    }


    private byte[] getMD5_15(String pwd, int times) {
        for (int i = 0; i < times; i++) {
            pwd = CipherUtils.md5(staticPWD + pwd + staticPWD) + staticPWD;
        }
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(pwd.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /**Error:Unable to load class 'sync_local_repo_ez6xbgdyvo2nvcw9k748p048h$_run_closure1$_closure2$_closure4$_closure5'.
         Possible causes for this unexpected error include:<ul><li>Gradle's dependency cache may be corrupt (this sometimes occurs after a network connection timeout.)
         <a href="syncProject">Re-download dependencies and sync project (requires network)</a></li><li>The state of a Gradle build process (daemon) may be corrupt. Stopping all Gradle daemons may solve this problem.
         <a href="stopGradleDaemons">Stop Gradle build processes (requires restart)</a></li><li>Your project may be using a third-party plugin which is not compatible with the other plugins in the project or the version of Gradle requested by the project.</li></ul>In the case of corrupt Gradle processes, you can also try closing the IDE and then killing all Java processes.
         * 去负数
         */
        byte md5_15[] = new byte[15];
        for (int i = 0; i < md5_15.length; i++) {
            md5_15[i] = (byte) (hash[i] & 0x7f);
        }
        return md5_15;
    }
}
