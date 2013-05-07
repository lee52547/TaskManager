package com.jd.bluedragon.sep.task.util;

import java.util.UUID;

/**
 * 前缀处理工具
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-23
 * Time: 下午6:40
 * To change this template use File | Settings | File Templates.
 */
public class PrefixUtil {

    public static String randomPrefix() {
        String prefix = UUID.randomUUID().toString();
        return prefix.toUpperCase().replaceAll("_","") + "_";
    }

}
