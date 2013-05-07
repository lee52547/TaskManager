package com.jd.bluedragon.sep.task.cache.collection;

/**
 * 环状队列
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-27
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public interface RingCacheQueue<T> extends CacheQueue<T> {
    /**
     * 返回头部员素
     * 被返回的元素将被移动到队尾
     * @return T
     */
    T next();
}
