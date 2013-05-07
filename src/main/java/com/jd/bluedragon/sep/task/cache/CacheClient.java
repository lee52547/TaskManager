package com.jd.bluedragon.sep.task.cache;

import com.jd.bluedragon.sep.task.cache.collection.CacheQueue;
import com.jd.bluedragon.sep.task.cache.collection.CacheSet;
import com.jd.bluedragon.sep.task.cache.collection.RingCacheQueue;

import java.io.Serializable;
import java.util.Set;

/**
 * 缓存接口
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-23
 * Time: 下午6:24
 * To change this template use File | Settings | File Templates.
 */
public abstract class CacheClient {

    /**
     * 根据key值获取队列
     * @param key 队列名称
     * @return 队列
     */
    public abstract <T> CacheQueue<T> getQueue(String key);


    /**
     * 获取一个环形队列
     * @param key key
     * @param <T> 类型l
     * @return 队列
     */
    public abstract <T> RingCacheQueue<T> getRingQueue(String key);


    /**
     * 设置缓存对象
     * @param key  key
     * @param value data
     */
    public abstract void set(String key,Object value);

    /**
     * 获取缓存对象
     * @param key key
     * @return Object
     */
    public abstract Object get(String key);

    /**
     * 校验缓存是否存在
     * @param key key值
     * @return  object
     */
    public abstract boolean contains(String key);

    /**
     * 缓存目前是否可用
     * @return boolean
     */
    public abstract boolean isAvailable();


    /**
     * 删除缓存
     * @param key  key
     */
    public abstract void delete(String key);

    /**
     * 根据key值的前缀来获取缓存
     *
     * @param prefix key前缀
     * @return 缓存对象列表
     */
    public abstract Set<Serializable> findByPrefix(String prefix);

    /**
     * 返回一个集合对象
     * @param key key
     * @return 集合对象j
     */
    public abstract  <T> CacheSet<T> getSet(String key);
}
