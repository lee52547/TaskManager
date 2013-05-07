package com.jd.bluedragon.sep.task.event;

import com.jd.bluedragon.sep.task.event.listener.Listener;

/**
 * 时间处理器
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-19
 * Time: 下午4:07
 * To change this template use File | Settings | File Templates.
 */
public interface EventHandler {
    /**
     * 处理特定的事件
     * @param et 事件类型
     */
    void handle(Event et);

    /**
     * 注册监听器
     * @param listener 监听器
     */
    void register(Listener listener);
}
