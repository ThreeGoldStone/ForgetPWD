package com.djl.javaUtils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.EditText;

import com.djl.androidutils.DJLUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {
    /**
     * @param uper
     * @param lower
     * @return 最简分式的字符串
     */
    public static String getSimplestFraction(int uper, int lower) {
        StringBuilder sb = new StringBuilder();
        int gcd = DJLMathUtils.getGCD(uper, lower);
        sb.append(uper / gcd).append("/").append(lower / gcd);
        return sb.toString();

    }

    // String a="\d";
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    // private final static Pattern IDcard = Pattern
    // .compile("/^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|
    // (^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$/");
    private final static Pattern phone = Pattern
            .compile("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$");

    /**
     * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(CharSequence input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     */
    public static boolean isEmail(CharSequence email) {
        if (isEmpty(email))
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 判断是不是一个格式正确的身份证
     */
    public static boolean isIDCard(CharSequence idCard) {
        //定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        //通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(idCard);
        //判断用户输入是否为身份证号
        return idNumMatcher.matches();
    }

    /**
     * 判断是不是一个合法的手机号码
     */
    public static boolean isPhone(CharSequence phoneNum) {
        if (isEmpty(phoneNum))
            return false;
        return phone.matcher(phoneNum).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * String转long
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        if (StringUtils.isEmpty(obj))
            return 0;
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
            return Math.round(StringUtils.toDouble(obj));
        }
    }

    /**
     * String转double
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static double toDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0D;
    }

    /**
     * 字符串转布尔
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 判断一个字符串是不是数字
     */
    public static boolean isNumber(CharSequence str) {
        try {
            Integer.parseInt(str.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断输入不是纯数字也不是纯字母
     */
    public static boolean isNumberAndLetter(CharSequence str) {
        boolean flag = false;

        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(str);
        if (m.matches()) {
//			Toast.makeText(Main.this,"输入的是数字", Toast.LENGTH_SHORT).show();
            flag = true;
        }
        p = Pattern.compile("[a-zA-Z]*");
        m = p.matcher(str);
        if (m.matches()) {
//			Toast.makeText(Main.this,"输入的是字母", Toast.LENGTH_SHORT).show();
            flag = true;
        }
        return !flag;

    }

    /**
     * 判断是纯汉字
     */
    public static boolean isChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]*");
        Matcher m;
        m = p.matcher(str);
//        for (int i = 0; i < str.length(); i++) {
//            m = p.matcher(str.substring(i, i + 1));
        if (!m.matches()) {
            return false;
        }
//        }
        return true;
    }


    /**
     * byte[]数组转换为16进制的字符串。
     *
     * @param data 要转换的字节数组。
     * @return 转换后的结果。
     */
    public static final String byteArrayToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.getDefault());
    }

    /**
     * 16进制表示的字符串转换为字节数组。
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] d = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            d[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(
                    s.charAt(i + 1), 16));
        }
        return d;
    }

    public static String showRate(double d) {
        d = d * 100;
        DecimalFormat df = new DecimalFormat("#,##0.##");
        return df.format(d);
    }

    /**
     * 输入的金额以分为单位
     *
     * @return 输出金额元为单位，保留两位小数
     */
    public static String ShowMoney(double input) {
        StringBuilder sb = new StringBuilder();
        if (input < 0) {
            sb.append("-");
            input = -input;
        }
        double d = input / 100;
        // int w = (int) (d / 10000);
        // int y = (int) (d / 100000000);
        // // double d2 = DJLMathUtils.round(d, 2);
        // if (y != 0) {
        // sb.append(DJLMathUtils.round(d / 100000000, 10)).append("亿"); 第一种现实方式
        // } else if (w != 0) {
        // sb.append(DJLMathUtils.round(d / 10000, 6)).append("万");
        // } else {
        // sb.append(DJLMathUtils.round(d, 2));
        // }
//        if (d >= 1.0) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        sb.append(df.format(d));
       /* } else if(d==0.0){
            return
        }else {
            sb.append(DJLMathUtils.round(d, 2));
        }*/
        return sb.toString();

    }

    /**
     * v.借款金额：百万以上，单位为万元（例如：210万元）；百万以下，单位为元（200,000万）
     * vi.还需金额：百万以上，单位为万元（例如：210万元）；百万以下，单位为元（200,000万）
     *
     * @param input
     * @return
     */
    public static String ShowMoneyBanWan(double input) {
        StringBuilder sb = new StringBuilder();
        if (input < 0) {
            sb.append("-");
            input = -input;
        }
        DecimalFormat df = new DecimalFormat("#,##0.00");
        double d = input / 100;
        if (d >= 1000000) {
            sb.append(df.format(d / 10000)).append("万");
        } else {
            sb.append(df.format(d));
        }
        return sb.toString();

    }

    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }

        return age;
    }

    /**
     * ip对应的message What 为555555
     *
     * @param handler
     */
    public static void getNetIpCallBack(final Handler handler) {
        new Thread() {
            public void run() {
                String netIp = GetNetIp();
                handler.obtainMessage(555555, netIp).sendToTarget();
            }
        }.start();
    }

    /**
     * 隐藏s中的从start到end之间的char为***
     *
     * @param s
     * @param start
     * @param end
     * @param hidLableWidth *号的个数，-1表示*号的个数为end-start
     * @return
     */

    public static String hide(String s, int start, int end, int hidLableWidth) {
        try {
            char[] bufferBefore = new char[start];
            s.getChars(0, start, bufferBefore, 0);
            char[] bufferAfter = new char[s.length() - end];
            s.getChars(end, s.length(), bufferAfter, 0);
            StringBuilder sb = new StringBuilder();
            sb.append(bufferBefore);
            if (hidLableWidth < 0) {
                hidLableWidth = end - start;
            }
            for (int i = 0; i < hidLableWidth; i++) {
                sb.append("*");
            }
            sb.append(bufferAfter);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 获取外网的IP(要访问Url，要放到后台线程里处理)
     *
     * @param @return
     * @return String
     * @throws
     * @Title: GetNetIp
     * @Description:
     */
    public static String GetNetIp() {
        URL infoUrl = null;
        InputStream inStream = null;
        String ipLine = "";
        HttpURLConnection httpConnection = null;
        try {
            infoUrl = new URL("http://ip168.com/");
            URLConnection connection = infoUrl.openConnection();
            httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,
                        "utf-8"));
                StringBuilder strber = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                    strber.append(line + "\n");

                Pattern pattern = Pattern
                        .compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}" +
                                "(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
                Matcher matcher = pattern.matcher(strber.toString());
                if (matcher.find()) {
                    ipLine = matcher.group();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inStream.close();
                httpConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ipLine;
    }

    /**
     * 获取本地IP
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            Enumeration<?> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) en.nextElement();
                Enumeration<?> enIp = ni.getInetAddresses();
                while (enIp.hasMoreElements()) {
                    InetAddress inet = (InetAddress) enIp.nextElement();
                    if (!inet.isLoopbackAddress() && (inet instanceof Inet4Address)) {
                        String string = inet.getHostAddress().toString();
                        DJLUtils.log("LocalIp = " + string);
                        return string;
                    }
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "0";
    }

    /**
     * 判断str是否为纯汉字
     *
     * @param str
     * @return
     */
    public static boolean isChineseOnly(String str) {

        char[] chars = str.toCharArray();
        boolean isGB2312 = true;
        for (int i = 0; i < chars.length; i++) {
            byte[] bytes = ("" + chars[i]).getBytes();
            if (bytes.length == 1) {
                // int[] ints = new int[2];
                // ints[0] = bytes[0] & 0xff;
                // ints[1] = bytes[1] & 0xff;
                // if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 &&
                // ints[1] <= 0xFE) {
                // } else {
                // isGB2312 = false;
                // break;
                // }
                // } else {
                isGB2312 = false;
                break;
            }
        }
        return isGB2312;
    }

    public static String[] vs = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c",
            "d", "e", "f", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
            "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * [0-9A-Za-z]的随机串
     *
     * @param length 位数
     * @return
     */
    public static String getRandom(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(vs[new Random().nextInt(vs.length)]);
        }
        return sb.toString();

    }
    /**
     * [0-9]的随机串
     *
     * @param length 位数
     * @return
     */
    public static String getRandomNum(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(vs[new Random().nextInt(10)]);
        }
        return sb.toString();

    }

    public static float toFloat(String obj) {
        try {
            return Float.parseFloat(obj);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取进度值，0.1/all以下的四舍五入，以上的取整
     *
     * @param process        分子
     * @param all            分母（必须大于0）
     * @param precisionCount 小数点后的位数
     * @return
     */
    public static double showProgress(double process, double all, int precisionCount) {

        return 0;
    }

    public static String base64(String s) {
        String messageF = null;
        try {
            byte[] bytes = s.getBytes("UTF-8");
            messageF = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            DJLUtils.log("UnsupportedEncodingException" + e.getMessage());
            e.printStackTrace();
        }
        return messageF;
    }


    public static void pwdFilter(EditText et) {
        InputFilter[] filters = et.getFilters();
        InputFilter[] inputFilters = null;
        if (filters != null) {
            inputFilters = Arrays.copyOf(filters, filters.length + 1);
        } else {
            inputFilters = new InputFilter[1];
        }
        inputFilters[inputFilters.length - 1] = new InputFilter() {
            String pwd = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "01234567890" +
                    "!@#$%^*.:~<![CDATA[&<>]]>?.,\\\"'|{}[]=+-_()";

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String sourceText = source.toString();
                String destText = dest.toString();
                //验证删除等按键
                if (TextUtils.isEmpty(sourceText)) {
                    return "";
                }
                boolean contains = true;
                for (int i = 0; i < sourceText.length(); i++) {
                    contains = contains && pwd.contains(sourceText.charAt(i) + "");
                }
                if (!contains) {
                    return "";
                }
                return dest.subSequence(dstart, dend) + sourceText;
            }
        };
        et.setFilters(inputFilters);
    }


    /**
     * 检查当前网络是否可用
     *
     * @param activity
     * @return
     */
    public static boolean isNetworkAvailable(Application activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
//                    System.out.println(i + "===状态===" + networkInfo[i].getState());
//                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmm");
    public static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss");
    public static SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getTime() {
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }

    public static String getTime1() {
        return dateFormat1.format(new Date(System.currentTimeMillis()));
    }

    public static String getTime2() {
        return dateFormat2.format(new Date(System.currentTimeMillis()));
    }

    public static String getTime3() {
        return dateFormat3.format(new Date(System.currentTimeMillis()));
    }

}
