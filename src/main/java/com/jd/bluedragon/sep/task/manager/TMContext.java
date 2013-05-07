package com.jd.bluedragon.sep.task.manager;

import com.jd.bluedragon.sep.task.TaskConfig;
import com.jd.bluedragon.sep.task.TaskHandler;
import com.jd.bluedragon.sep.task.cache.TaskCache;
import com.jd.bluedragon.sep.task.cache.TaskQueueAllocator;
import com.jd.bluedragon.sep.task.service.TaskService;


/**
 * 任务管理器上下文
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-19
 * Time: 下午2:09
 * To change this template use File | Settings | File Templates.
 */
public class TMContext {

    private TaskCache taskCache;
    private TaskService taskService;
    private TaskConfig config;
    private TaskQueueAllocator taskQueueAllocator;


    public TMContext() {
        this.config = new TaskConfig();
    }

    public TaskCache getTaskCache() {
        return taskCache;
    }

    public void setTaskCache(TaskCache taskCache) {
        this.taskCache = taskCache;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }


    public TaskConfig getConfig() {
        return config;
    }

    public void setConfig(TaskConfig config) {
        this.config = config;
    }

    public TaskQueueAllocator getTaskQueueAllocator() {
        return taskQueueAllocator;
    }

    public void setTaskQueueAllocator(TaskQueueAllocator taskQueueAllocator) {
        this.taskQueueAllocator = taskQueueAllocator;
    }


}
