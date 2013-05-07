package com.jd.bluedragon.sep.task.util;

/**
 * 数学工具
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-5-6
 * Time: 下午2:42
 * To change this template use File | Settings | File Templates.
 */
public class MathUtil {

    /**
     * 求两个数的最大公约数
     * @param a 参数a
     * @param b 参数b
     * @return a和b的最大公约数
     */
    public static int maxCommFactor(int a,int b) {
        if(a < b) {
            int temp = a;
            a = b;
            b = temp;
        }
        int r;
        while (a % b != 0) {
            r = a % b;
            a = b;
            b = r;
        }
        return b;
    }

}
