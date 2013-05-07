package com.jd.bluedragon.sep.task.event.listener;

import com.jd.bluedragon.sep.task.event.EventType;

/**
 * 监听器
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-19
 * Time: 下午3:44
 * To change this template use File | Settings | File Templates.
 */
public abstract class Listener {

    private EventType eventType;

    public abstract void excute();

    public Listener(EventType eventType) {
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType.getCode();
    }
}
