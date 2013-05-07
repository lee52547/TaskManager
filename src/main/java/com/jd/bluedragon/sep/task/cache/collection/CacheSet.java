package com.jd.bluedragon.sep.task.cache.collection;


/**
 * 缓存Set
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-18
 * Time: 上午10:27
 * To change this template use File | Settings | File Templates.
 */
public interface CacheSet<T> extends Iterable<T> {


    /**
     * 添加数据
     * @param t 数据
     */
    void add(T t);

    /**
     * 长度
     * @return long
     */
    long size();

    /**
     * 队列的名字
     * @return string
     */
    String getName();


    /**
     * 从集合中删除对象
     * @param t 要删除的元素
     */
    void remove(T t);
}
