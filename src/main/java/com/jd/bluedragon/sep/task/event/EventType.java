package com.jd.bluedragon.sep.task.event;

/**
 * 事件类型
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-19
 * Time: 下午3:56
 * To change this template use File | Settings | File Templates.
 */
public enum EventType {

    AllTasksCompleted(0,"任务全部完成"),TasksBackUp(1,"任务积压");

    private Integer code;
    private String description;

    private EventType(Integer code,String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

}
