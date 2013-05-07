package com.jd.bluedragon.sep.task.cache.collection;

import java.util.List;

/**
 * 缓存队列
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-18
 * Time: 上午10:27
 * To change this template use File | Settings | File Templates.
 */
public interface CacheQueue<T> {

    /**
     * 获取队首的对象
     * @param n 要从队列头部获取的对象数量
     * @return 队列头部的对象
     */
    List<T> shift(Integer n);

    /**
     * 将数据压入队尾
     * @param t 数据
     */
    void push(T t);

    /**
     * 队列长度
     * @return long
     */
    long size();

    /**
     * 队列的名字
     * @return string
     */
    String getName();

    /**
     * 将数据列表压入队列
     * @param dbData 数据列表
     * @param less 起始位置
     */
    <T> void pushList(List<T> dbData, int less);

    /**
     * 将列表中的数据全部添加到队列中
     * @param data 数据列表
     */
    <T> void pushAll(List<T> data);

    /**
     * 返回队首元素 但是不从队列中弹出
     * @return T
     */
    T head();
}
