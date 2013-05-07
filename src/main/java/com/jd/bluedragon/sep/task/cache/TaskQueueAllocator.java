package com.jd.bluedragon.sep.task.cache;

import com.jd.bluedragon.sep.task.Task;

/**
 * 队列分配接口
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-18
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
public interface TaskQueueAllocator {
    /**
     * 根据传入的任务对象 计算任务所属的队列名
     * @param t 任务
     * @param <T> 任务类
     * @return 任务所属队列
     */
    <T extends Task> String allocat(T t);

    /**
     * 队列统一使用的前缀
     * @return prefix
     */
    String getPrefix();
}
