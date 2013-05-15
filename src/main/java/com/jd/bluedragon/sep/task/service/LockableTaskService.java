package com.jd.bluedragon.sep.task.service;

import com.jd.bluedragon.sep.task.Task;

import java.util.List;

/**
 * 将任务在DB锁定的接口
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-25
 * Time: 下午3:20
 * To change this template use File | Settings | File Templates.
 */
public interface LockableTaskService extends TaskService {

    /**
     * 将任务拥有者锁定为指定用户 其他人不能获取
     *
     * @param userId   用户ID
     * @param dataList 任务列表
     */
    void lockTasks(Integer userId, List<? extends Task> dataList);

    /**
     * 将任务解锁 使得其它用户能够获取到任务
     *
     * @param data 任务列表
     */
    void unlockTasks(List<? extends Task> data);

    /**
     * 放弃某一用户的当前任务
     *
     * @param userId 用户ID
     */
    void giveUpTasks(Integer userId);

    /**
     * 检查列表中的任务是否已经完成
     *
     * @param data 任务列表
     * @return 未完成的任务列表
     */
    List<? extends Task> getUnCompleted(List<? extends Task> data);

}
