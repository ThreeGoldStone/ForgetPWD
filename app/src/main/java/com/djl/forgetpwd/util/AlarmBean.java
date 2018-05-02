package com.djl.forgetpwd.util;

import com.djl.javaUtils.StringUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by DJl on 2017/9/18.
 * email:1554068430@qq.com
 */

public class AlarmBean {
    public String title;
    public String sound;
    public String time;

    /**
     * @param row 格式
     * @return
     */
    public static AlarmBean parse(String row) {
        AlarmBean alarmBean = null;
        try {
            String[] properties = row.split(" ");
            int index = 0;
            alarmBean = new AlarmBean();
            for (String property : properties) {
                String trim = property.trim();
                if (index < 3 && !StringUtils.isEmpty(trim)) {
                    switch (index) {
                        case 0:
                            alarmBean.time = trim;
                            break;
                        case 1:
                            alarmBean.title = trim;
                            break;
                        case 2:
                            alarmBean.sound = trim;
                            break;
                    }
                    index++;
                }
            }
            if (index != 3) alarmBean = null;
        } catch (Exception e) {
            e.printStackTrace();
            alarmBean = null;
        }

        return alarmBean;
    }

    /**
     * @param rowsString 格式 yyyy年MM月dd日HH时mm分ss秒/+yyyy年MM月dd日HH时mm分ss秒 title soundCode
     *                   yyyy年MM月dd日HH时mm分ss秒/+yyyy年MM月dd日HH时mm分ss秒 title soundCode
     *                   yyyy年MM月dd日HH时mm分ss秒/+yyyy年MM月dd日HH时mm分ss秒 title soundCode
     * @return
     */
    public static ArrayList<AlarmBean> parses(String rowsString) {
        String[] rows = rowsString.split("\n");
        ArrayList<AlarmBean> been = new ArrayList<>();
        for (String row : rows) {
            AlarmBean bean = AlarmBean.parse(row);
            if (bean != null) {
                been.add(bean);
            }
        }
        for (AlarmBean alarmBean : been) {
            System.out.println(alarmBean.toString());
        }
        return been;
    }

    @Override
    public String toString() {
        return "{" +
                "\"title\":\"" + title + '\"' +
                ", \"sound\":\"" + sound + '\"' +
                ", \"time\":\"" + time + '\"' +
                '}';
    }

    static String[] timeMark = {"年", "月", "日", "时", "分", "秒"};

    public static Date parseTime(String timeString) {

        String[] split = timeString.split(timeMark[0]);
        int year = 2017;
        int m = 0;
        if (split.length == 2) {
            String data = split[0].trim();
            timeString = split[1];
            if (!StringUtils.isEmpty(data)) {
                year = Integer.parseInt(data);
            }
        } else {
            split = timeString.split(timeMark[0]);
            if (split.length == 2) {
                String data = split[0].trim();
                timeString = split[1];
                if (!StringUtils.isEmpty(data)) {
                    year = Integer.parseInt(data);
                }
            }
        }

        return null;
    }
}
