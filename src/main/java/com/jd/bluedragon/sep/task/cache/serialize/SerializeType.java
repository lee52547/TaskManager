package com.jd.bluedragon.sep.task.cache.serialize;

/**
 * 事件类型
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-19
 * Time: 下午3:56
 * To change this template use File | Settings | File Templates.
 */
public enum SerializeType {

    Json(0,"JSON"),Object(1,"OBJECT");

    private Integer code;
    private String description;

    private SerializeType(Integer code, String description) {
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
