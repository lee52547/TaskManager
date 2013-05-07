package com.jd.bluedragon.sep.task.event;

import com.jd.bluedragon.sep.task.Task;

/**
 * 事件
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-19
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 */
public class Event<T extends Task> {

    private T data;
    private int evenType;

    public Event(T data, int evenType) {
        this.data = data;
        this.evenType = evenType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getEvenType() {
        return evenType;
    }

    public void setEvenType(int evenType) {
        this.evenType = evenType;
    }
}
