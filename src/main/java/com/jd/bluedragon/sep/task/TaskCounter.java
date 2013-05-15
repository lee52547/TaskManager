package com.jd.bluedragon.sep.task;

import com.jd.bluedragon.sep.task.manager.TaskManager;

/**
 * 任务计数器
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-19
 * Time: 下午6:49
 * To change this template use File | Settings | File Templates.
 */
public class TaskCounter {

    private TaskManager taskManager;
    private String name;

    public TaskCounter(TaskManager taskManager, Task t) {
        this.taskManager = taskManager;
        this.name = taskManager.getGen().getRQueueName(t);
    }

    public long add(int n) {
        long c = count();
        c += n;
        taskManager.getCacheClient().set(name,c);
        return c;
    }

    public long count() {
        long num = 0;
        Object nbj = taskManager.getCacheClient().get(name);
        if (nbj != null) {
            num = Integer.valueOf(nbj.toString());
        }
        return num;
    }

}
