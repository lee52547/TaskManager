package com.jd.bluedragon.sep.task.service;

import com.jd.bluedragon.sep.task.Task;

import java.util.List;

/**
 * 任务处理接口 用于任务管理器与DB的交互
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-18
 * Time: 上午11:47
 * To change this template use File | Settings | File Templates.
 */
public interface TaskService {
    /**
     * 直接从数据库获取任务
     *
     *
     * @param t 匹配条件
     * @param frameSize  每次获取的任务数量
     * @return 任务列表
     */
    List<? extends Task> getTasks(Task t, long frameSize);

    /**
     * 将完成的任务写入到数据库
     * @param userId 用户ID
     * @param tasks 完成的任务列表
     * @return 成功写入数据库的任务数量
     */
     int completeTasks(Integer userId, List<? extends Task> tasks);

    /**
     * 新增任务到数据库
     * @param t 任务对象
     */
    void addTask(Task t);

}
