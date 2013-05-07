package com.jd.bluedragon.sep.task.cache;

import com.jd.bluedragon.sep.task.Task;
import com.jd.bluedragon.sep.task.TaskFrame;
import com.jd.bluedragon.sep.task.cache.collection.CacheQueue;
import com.jd.bluedragon.sep.task.cache.collection.CacheSet;
import com.jd.bluedragon.sep.task.cache.serialize.JsonSerializer;
import com.jd.bluedragon.sep.task.cache.serialize.TaskSerializer;
import com.jd.bluedragon.sep.task.manager.TaskManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务缓存接口
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-18
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
public class TaskCache {

    private final TaskManager taskManager;
    private final TaskSerializer taskSerializer;

    protected TaskCache(TaskManager taskManager) {
        this.taskManager = taskManager;
        switch (taskManager.getContext().getConfig().getSerializeType()) {
            case 0:   //SerializeType.Json
                this.taskSerializer = new JsonSerializer();
                break;
            default:
                this.taskSerializer = new JsonSerializer();
        }
    }

    /**
     * 获取用户任务帧
     *
     * @param userId 用户ID
     * @param clazz  任务类class
     * @return 任务帧
     */
    public TaskFrame getUserTask(Integer userId, Class<? extends Task> clazz) {
        Serializable ot = taskManager.getGen().getUserTaskKey(userId);
        if (ot != null) {
            return taskSerializer.unserialize(ot, clazz);
        }
        return null;
    }


    /**
     * 删除用户任务帧
     *
     * @param userId 用户ID
     * @param clazz  任务类型
     */
    public void deleteUserTask(Integer userId, Class<? extends Task> clazz) {
        Serializable ot = taskManager.getGen().getUserTaskKey(userId);
        if (ot != null) {
            TaskFrame frame = taskSerializer.unserialize(ot, clazz);
            taskManager.getCacheClient().delete(taskManager.getGen().getUserTaskKey(userId));
            CacheSet<Integer> set = taskManager.getCacheClient().getSet(frame.getToken());
            set.remove(userId);
        }
    }

    /**
     * 缓存是否可用
     *
     * @return boolean
     */
    public boolean isAvailable() {
        return taskManager.getCacheClient().isAvailable();
    }

    /**
     * 返回指定名称的队列对象
     * 不会去验证缓存是否真的存在这个队列
     *
     * @param name 队列名字
     * @return 队列对象
     */
    public <T> CacheQueue<T> getQueue(String name) {
        return taskManager.getCacheClient().getQueue(name);
    }

    /**
     * 获取所有用户持有的任务帧
     *
     * @param token 令牌
     * @return 任务帧列表
     */
    public List<TaskFrame> getAllUserTasks(String token) {
        List<TaskFrame> list = new ArrayList<TaskFrame>();
        CacheSet<Integer> set = taskManager.getCacheClient().getSet(token);
        if (set != null && set.size() > 0) {
            for (Serializable s : set) {
                list.add(taskSerializer.unserialize(s.toString()));
            }
        }
        return list;
    }

    /**
     * 添加用户任务
     *
     * @param userId   用户ID
     * @param tasks    任务列表
     * @param srcQueue 源队列
     * @return 生成的任务帧
     */
    public TaskFrame addUserTask(Integer userId, List<? extends Task> tasks, String srcQueue) {
        TaskFrame task = new TaskFrame();
        task.setData(tasks);
        task.setSrcQueue(srcQueue);
        task.setStaffNo(userId);
        task.setTime(System.currentTimeMillis());
        return addUserTask(task);
    }


    /**
     * 添加用户任务
     * @param userId 用户ID
     * @param tasks  任务对象
     * @param srcQueue 原始队列
     * @param token 令牌
     * @return 任务帧
     */
    public TaskFrame addUserTask(Integer userId, List<Task> tasks, String srcQueue, String token) {
        if (tasks.size() > 0) {
            TaskFrame task = new TaskFrame();
            task.setData(tasks);
            task.setSrcQueue(srcQueue);
            task.setStaffNo(userId);
            task.setTime(System.currentTimeMillis());
            task.setToken(token);
            return task;
        }else {
            //clear
            CacheSet<Integer> set = taskManager.getCacheClient().getSet(token);
            set.remove(userId);
            taskManager.getCacheClient().delete(taskManager.getGen().getUserTaskKey(userId));
        }
        return null;
    }

    /**
     * 缓存任务
     *
     * @param task 任务对象
     */
    public TaskFrame addUserTask(TaskFrame task) {
        if (task != null && task.getStaffNo() != null) {
            if (task.getToken() == null) {
                task.setToken(taskManager.getToken());
            }
            taskManager.getCacheClient().set(taskManager.getGen().getUserTaskKey(task.getStaffNo()), taskSerializer.serialize(task));
            CacheSet<Integer> set = taskManager.getCacheClient().getSet(task.getToken());
            set.add(task.getStaffNo());
            return task;
        }
        return null;
    }
}
