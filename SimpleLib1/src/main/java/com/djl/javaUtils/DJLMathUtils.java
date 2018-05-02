package com.djl.javaUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public final class DJLMathUtils {
    private static final int ZONE_FLAG_FRONT = 0;
    private static final int ZONE_FLAG_BEHIND = 1;
    private static final int ZONE_FLAG_INDEPENDENT = 2;

    /**
     * 获取两个数最大公约数
     *
     * @return
     */
    public static int getGCD(int a, int b) {
        int min = Math.abs(a);
        int max = Math.abs(b);
        if (a > b) {
            min = b;
            max = a;
        }
        if (min == 0)
            return max;
        else
            return getGCD(min, max - min);

    }

    /**
     * @param target   需要判断所在区域的数字
     * @param zoneType 分区类型 前包含{@link #ZONE_FLAG_BEHIND}, 后包含{@link #ZONE_FLAG_FRONT}
     *                 , 不包含{@link #ZONE_FLAG_INDEPENDENT}
     * @param points   分区节点(可以是未排序的),排序为升序
     * @param points1  分区节点(可以是未排序的),排序为升序
     * @return 返回0~n 代表从前往后的的分区位置,异常返回-1
     */
    public static int zone(int target, int zoneType, int[] points,
                           int... points1) {
        if (points == null)
            points = points1;
        points = sort(removeDuplicates(points), true);
        switch (zoneType) {
            /**
             * 前包含
             */
            case ZONE_FLAG_FRONT:

                for (int j = 0; j < points.length - 1; j++) {
                    if (points[j] <= target && target < points[j + 1]) {
                        return j + 1;
                    }
                }
                if (target < points[0])
                    return 0;
                if (target >= points[points.length - 1])
                    return points.length;
                break;
            /**
             * 后包含
             */
            case ZONE_FLAG_BEHIND:
                for (int j = 0; j < points.length - 1; j++) {
                    if (points[j] < target && target <= points[j + 1]) {
                        return j + 1;
                    }
                }
                if (target <= points[0])
                    return 0;
                if (target > points[points.length - 1])
                    return points.length;
                break;
            /**
             * 不包含
             */
            case ZONE_FLAG_INDEPENDENT:
                for (int j = 0; j < points.length - 1; j++) {
                    if (target == points[j])
                        return (j + 1) * 2 - 1;
                    if (points[j] < target && target < points[j + 1]) {
                        return (j + 1) * 2;
                    }
                }
                if (target < points[0])
                    return 0;
                if (target > points[points.length - 1])
                    return points.length * 2 + 1;
                if (target == points[points.length - 1])
                    return points.length * 2;
                break;
            /**
             * 默认是前包含的
             */
            default:
                for (int j = 0; j < points.length - 1; j++) {
                    if (points[j] <= target && target < points[j + 1]) {
                        return j + 1;
                    }
                }
                if (target < points[0])
                    return 0;
                if (target >= points[points.length - 1])
                    return points.length;
                break;
        }
        return -1;

    }

    /**
     * @param target   需要判断所在区域的数字
     * @param zoneType 分区类型 前包含{@link #ZONE_FLAG_BEHIND}, 后包含{@link #ZONE_FLAG_FRONT}
     *                 , 不包含{@link #ZONE_FLAG_INDEPENDENT}
     * @param points   分区节点(可以是未排序的),排序为升序
     * @param points1  分区节点(可以是未排序的),排序为升序
     * @return 返回0~n 代表从前往后的的分区位置,异常返回-1
     */
    public static int zone(double target, int zoneType, double[] points,
                           double... points1) {
        if (points == null)
            points = points1;
        points = sort(removeDuplicates(points), true);
        switch (zoneType) {
            /**
             * 前包含
             */
            case ZONE_FLAG_FRONT:

                for (int j = 0; j < points.length - 1; j++) {
                    if (points[j] <= target && target < points[j + 1]) {
                        return j + 1;
                    }
                }
                if (target < points[0])
                    return 0;
                if (target >= points[points.length - 1])
                    return points.length;
                break;
            /**
             * 后包含
             */
            case ZONE_FLAG_BEHIND:
                for (int j = 0; j < points.length - 1; j++) {
                    if (points[j] < target && target <= points[j + 1]) {
                        return j + 1;
                    }
                }
                if (target <= points[0])
                    return 0;
                if (target > points[points.length - 1])
                    return points.length;
                break;
            /**
             * 不包含
             */
            case ZONE_FLAG_INDEPENDENT:
                for (int j = 0; j < points.length - 1; j++) {
                    if (target == points[j])
                        return (j + 1) * 2 - 1;
                    if (points[j] < target && target < points[j + 1]) {
                        return (j + 1) * 2;
                    }
                }
                if (target < points[0])
                    return 0;
                if (target > points[points.length - 1])
                    return points.length * 2 + 1;
                if (target == points[points.length - 1])
                    return points.length * 2;
                break;
            /**
             * 默认是前包含的
             */
            default:
                for (int j = 0; j < points.length - 1; j++) {
                    if (points[j] <= target && target < points[j + 1]) {
                        return j + 1;
                    }
                }
                if (target < points[0])
                    return 0;
                if (target >= points[points.length - 1])
                    return points.length;
                break;
        }
        return -1;

    }

    /**
     * 移除数组中重复的元素
     *
     * @param withRepeat 含有重复元素的数组
     * @return
     */
    public static int[] removeDuplicates(int[] withRepeat) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < withRepeat.length; i++) {
            if (!list.contains(withRepeat[i])) {
                list.add(withRepeat[i]);
            }
        }
        int[] is = new int[list.size()];
        for (int i = 0; i < is.length; i++) {
            is[i] = list.get(i);
        }
        return is;
    }

    /**
     * 移除数组中重复的元素
     *
     * @param withRepeat 含有重复元素的数组
     * @return
     */
    public static double[] removeDuplicates(double[] withRepeat) {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < withRepeat.length; i++) {
            if (!list.contains(withRepeat[i])) {
                list.add(withRepeat[i]);
            }
        }
        double[] is = new double[list.size()];
        for (int i = 0; i < is.length; i++) {
            is[i] = list.get(i);
        }
        return is;
    }

    /**
     * @param is        输入的数组
     * @param sort_flag 升序true,降序false
     * @return
     */
    public static int[] sort(int[] is, boolean sort_flag) {
        for (int i = 0; i < is.length; i++) {
            for (int j = 0; j < is.length - i - 1; j++) {
                int temp;
                if (sort_flag) {
                    if (is[j] > is[j + 1]) {
                        temp = is[j];
                        is[j] = is[j + 1];
                        is[j + 1] = temp;
                    }
                } else {
                    if (is[j] < is[j + 1]) {
                        temp = is[j];
                        is[j] = is[j + 1];
                        is[j + 1] = temp;
                    }
                }

            }
        }
        return is;
    }

    /**
     * @param is        输入的数组
     * @param sort_flag 升序true,降序false
     * @return
     */
    public static double[] sort(double[] is, boolean sort_flag) {
        for (int i = 0; i < is.length; i++) {
            for (int j = 0; j < is.length - i - 1; j++) {
                double temp;
                if (sort_flag) {
                    if (is[j] > is[j + 1]) {
                        temp = is[j];
                        is[j] = is[j + 1];
                        is[j + 1] = temp;
                    }
                } else {
                    if (is[j] < is[j + 1]) {
                        temp = is[j];
                        is[j] = is[j + 1];
                        is[j + 1] = temp;
                    }
                }

            }
        }
        return is;
    }

    /**
     * @param f
     * @param i 传入0就是四舍五入到整数 ，传入1就保留到小数点后一位，反之就是小数点前
     * @return
     */
    public static double round(double f, int i) {
        BigDecimal b = new BigDecimal(f);
        double f1 = b.setScale(i, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;

    }

    /**
     * 乘法  避免double计算的误差 a*b
     *
     * @param a
     * @param b
     * @return
     */
    public static double multiply(double a, double b) {
        BigDecimal bda = new BigDecimal(a);
        BigDecimal bdb = new BigDecimal(b);
        return bda.multiply(bdb, MathContext.DECIMAL64).doubleValue();
    }

    /**
     * 除法  避免double计算的误差 a/b
     *
     * @param a
     * @param b
     * @return
     */
    public static double divide(double a, double b) {
        BigDecimal bda = new BigDecimal(a);
        BigDecimal bdb = new BigDecimal(b);
        return bda.divide(bdb, MathContext.DECIMAL64).doubleValue();
    }


}
