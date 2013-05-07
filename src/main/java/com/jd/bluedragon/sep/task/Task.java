package com.jd.bluedragon.sep.task;

/**
 * 抽象任务类
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-19
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
public abstract class Task {
    /**
     * 获取用户ID
     * @return  ID
     */
    public abstract String getId();

    /**
     * 返回此对象的签名
     * 当Task对象用作查询条件时 能够根据其中条件值的不同返回不同的签名字符串
     * 任务管理器 通过不同的sign值来将查询结果分到不同的队列
     * @return sign
     */
    public abstract String sign();
}
