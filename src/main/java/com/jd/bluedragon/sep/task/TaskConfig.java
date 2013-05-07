package com.jd.bluedragon.sep.task;

import com.jd.bluedragon.sep.task.cache.serialize.SerializeType;

/**
 * 任务管理设置
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-18
 * Time: 上午11:49
 * To change this template use File | Settings | File Templates.
 */
public class TaskConfig {


    private Integer queueLen = 10;
    private Integer frameSize = 10;
    private double loadFactor = 0.2;
    private Integer recycleTime = -1;
    private boolean strict = false;
    private String dataType;
    private Integer serializeType = SerializeType.Json.getCode();
    private Integer serverNum;

    public boolean isStrict() {
        return strict;
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }

    public Integer getQueueLen() {
        return queueLen;
    }

    public void setQueueLen(Integer queueLen) {
        this.queueLen = queueLen;
    }

    public Integer getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(Integer frameSize) {
        this.frameSize = frameSize;
    }

    public double getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(double loadFactor) {
        this.loadFactor = loadFactor;
    }

    public int getLoadNum() {
        return (int) (queueLen * frameSize * loadFactor);
    }

    public long realLen() {
        return frameSize * queueLen;
    }

    public Integer getRecycleTime() {
        return recycleTime;
    }

    public void setRecycleTime(Integer recycleTime) {
        this.recycleTime = recycleTime;
    }

    public boolean isStrictModel() {
        return this.strict;
    }

    public Integer getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(Integer serializeType) {
        this.serializeType = serializeType;
    }

    public void setDataType(String dataType) {
        if(dataType != null) {
            dataType = dataType.trim().toUpperCase();
            if(dataType.equals("")) {
                return;
            }
            for(SerializeType st : SerializeType.values()) {
                if(st.getDescription().equals(dataType)) {
                    this.serializeType = st.getCode();
                    break;
                }
            }
        }
    }

    public void setServerNum(Integer serverNum) {
        this.serverNum = serverNum;
    }

    public Integer getServerNum() {
        return serverNum;
    }
}
