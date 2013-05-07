package com.jd.bluedragon.sep.task.cache.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jd.bluedragon.sep.task.Task;
import com.jd.bluedragon.sep.task.TaskFrame;
import com.jd.bluedragon.sep.task.util.JsonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Json序列化工具
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-23
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public class JsonSerializer implements TaskSerializer {

    @Override
    public Serializable serialize(TaskFrame taskFrame) {
        return JsonUtil.toJson(taskFrame);
    }

    @Override
    public TaskFrame unserialize(Serializable s, Class<? extends Task> clazz) {
        return json2TF(s.toString(), clazz);
    }

    @Override
    public TaskFrame unserialize(Serializable s) {
        return JsonUtil.jsonToBean(s.toString(),TaskFrame.class);
    }

    private TaskFrame json2TF(String json, Class<? extends Task> clazz) { TaskFrame taskFrame = new TaskFrame();
        JSONObject jo = JSON.parseObject(json);
        taskFrame.setTime(jo.getLong("time"));
        taskFrame.setStaffNo(jo.getInteger("staffNo"));
        taskFrame.setSrcQueue(jo.getString("srcQueue"));
        JSONArray jrr = jo.getJSONArray("data");
        List<Task> list = new ArrayList<Task>();
        for (int i = 0; i < jrr.size(); i++) {
            list.add(jrr.getObject(i, clazz));
        }
        taskFrame.setData(list);
        return taskFrame;
    }

}
