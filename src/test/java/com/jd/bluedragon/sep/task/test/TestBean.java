package com.jd.bluedragon.sep.task.test;

import com.jd.bluedragon.sep.task.Task;

/**
 * 测试TaskBean
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-18
 * Time: 下午3:37
 * To change this template use File | Settings | File Templates.
 */
public class TestBean extends Task {

    private String id;
    private String name;
    private Integer key;

    public TestBean() {
    }

    public TestBean(String id,String name, Integer key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    @Override
    public String getId() {
        return this.id;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String sign() {
        return null;
    }


}
