package com.jd.bluedragon.sep.task;

import com.jd.bluedragon.sep.task.cache.collection.CacheQueue;
import com.jd.bluedragon.sep.task.manager.TaskManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务队列
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-19
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public class TaskQueue<T extends Task> {

    private TaskManager taskManager;
    private Task task;

    private CacheQueue<T> queue;
    private String name;
    private TaskCounter counter;

    public TaskQueue(TaskManager taskManager, Task t) {
        this.taskManager = taskManager;
        this.task = t;
        this.queue = taskManager.getContext().getTaskCache().getQueue(taskManager.getGen().getQueueName(t));
        this.name = queue.getName();
        this.counter = new TaskCounter(taskManager,t);
    }

    /**
     * 返回队列名称
     *
     * @return 队列名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 根据上下文从指定队列获取指定数量的任务
     *
     * @return 任务列表
     */
    public List<T> shift() {

        List<T> list = queue.shift(taskManager.getContext().getConfig().getFrameSize());
        if (queue.size() == 0 && taskSize() > 0) {
            List<? extends Task> dbData = null;
            synchronized (this) {
                if (queue.size() == 0) {
                    dbData = taskManager.getContext().getTaskService().getTasks(task, taskManager.getContext().getConfig().realLen());
                    if (list == null) {
                        list = new ArrayList<T>(taskManager.getContext().getConfig().getFrameSize());
                    }
                    if (dbData != null && dbData.size() > 0) {
                        int less = taskManager.getContext().getConfig().getFrameSize() - list.size();
                        for (int i = 0; i < less; i++) {
                            if (dbData.size() > i) {
                                list.add((T) dbData.get(i));
                            }
                        }
                        queue.pushList(dbData, less);
                    }
                }
            }
        }
        if(list != null) {
            counter.add(list.size() * -1);
        }
        return list;
    }


    /**
     * 返回队列中当前任务数量
     *
     * @return size
     */
    public long size() {
        return queue.size();
    }

    /**
     * 向任务队列中添加任务
     *
     * @param t 任务
     */
    public boolean push(T t) {
        counter.add(1);
        if (queue.size() >= taskManager.getContext().getConfig().realLen()) {
            return false;
        }
        queue.push(t);
        return true;
    }

    /**
     * 返回数据库中任务实际数量
     * @return 任务数量
     */
    public long taskSize() {
        return counter.count();
    }
}
