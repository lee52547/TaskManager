package com.jd.bluedragon.sep.task.cache.serialize;

import com.jd.bluedragon.sep.task.Task;
import com.jd.bluedragon.sep.task.TaskFrame;

import java.io.Serializable;

/**
 * 对象序列化接口
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-23
 * Time: 下午2:00
 * To change this template use File | Settings | File Templates.
 */
public interface TaskSerializer {

    /**
     * 将对象序列化
     * @param taskFrame 需要序列化的对象
     * @return 序列化之后的对象
     */
    Serializable serialize(TaskFrame taskFrame);


    /**
     * 反序列化
     * @param s 被序列化之后的对象
     * @param clazz 要被转成的类型
     * @return 对象
     */
    TaskFrame unserialize(Serializable s,Class<? extends Task> clazz);

    /**
     * 反序列化
     * @param s 被序列化之后的对象
     * @return 对象
     */
    TaskFrame unserialize(Serializable s);
}
