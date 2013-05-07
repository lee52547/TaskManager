package com.jd.bluedragon.sep.task;


import java.util.List;

/**
 * 当前任务
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-16
 * Time: 下午3:53
 * To change this template use File | Settings | File Templates.
 */
public class TaskFrame {

    private Integer staffNo;
    private Long time;
    private List<? extends Task> data;
    private String srcQueue;
    private String token;

    public Integer getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(Integer staffNo) {
        this.staffNo = staffNo;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public List<? extends Task> getData() {
        return data;
    }

    public void setData(List<? extends Task> data) {
        this.data = data;
    }

    public String getSrcQueue() {
        return srcQueue;
    }

    public void setSrcQueue(String srcQueue) {
        this.srcQueue = srcQueue;
    }


    public boolean isExpried() {
        return this.getTime() == null || System.currentTimeMillis() - this.getTime() > 1000 * 60 * 30;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
