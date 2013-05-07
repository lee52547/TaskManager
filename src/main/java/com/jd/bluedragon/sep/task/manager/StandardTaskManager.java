package com.jd.bluedragon.sep.task.manager;

import com.jd.bluedragon.sep.task.*;
import com.jd.bluedragon.sep.task.event.Event;
import com.jd.bluedragon.sep.task.event.EventType;

import java.util.*;

/**
 * 标准任务管理器
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-18
 * Time: 上午11:44
 * To change this template use File | Settings | File Templates.
 */
public class StandardTaskManager extends TaskManager {


    @Override
    public long countTask(Task t) {
        TaskQueue<? extends Task> tq = new TaskQueue<Task>(this, t);
        return tq.taskSize();
    }

    @Override
    public List<? extends Task> getTask(Integer userId, Task t) {
        List<? extends Task> dataList = null;
        if (context.getTaskCache().isAvailable()) {
            TaskFrame task = context.getTaskCache().getUserTask(userId,this.getClazz());
            //getTaskFrom TaskQueue
            if (task == null) {
                TaskQueue<? extends Task> tq = new TaskQueue<Task>(this, t);
                dataList = tq.shift();
                if (dataList.size() > 0) {
                    if (context.getConfig().isStrictModel()) {
                        context.getTaskService().lockTasks(userId, dataList);
                    }
                    context.getTaskCache().addUserTask(userId, dataList, tq.getName());
                }

                if (dataList.size() > 0 && tq.size() == 0) {
                    eventHandler.handle(new Event<Task>(t, EventType.AllTasksCompleted.getCode()));
                }
            }

        } else {
            return context.getTaskService().getTasks(t, context.getConfig().getFrameSize());
        }
        return dataList;
    }


    @Override
    public Integer completeTasks(Integer userId, List<? extends Task> tasks) {
        if (tasks == null || tasks.size() == 0) {
            return 0;
        }
        //不论缓存是否可用 都调用数据库
        int successNum = context.getTaskService().completeTasks(userId, tasks);
        if (context.getTaskCache().isAvailable()) {
            Set<Object> set = new HashSet<Object>();
            TaskFrame userTask = context.getTaskCache().getUserTask(userId, this.getClazz());
            if (userTask != null && userTask.getData() != null) {
                for (Task t : tasks) {
                    set.add(t.getId());
                }
                List<Task> unCompleteTask = new ArrayList<Task>();
                for (Task ut : userTask.getData()) {
                    if (!set.contains(ut.getId())) {
                        unCompleteTask.add(ut);
                    }
                }
                //将未完成的任务重新放回到用户缓存中
                //删除缓存
                context.getTaskCache().addUserTask(userId, unCompleteTask, userTask.getSrcQueue(),userTask.getToken());
            }
        }
        return successNum;
    }

    @Override
    public void giveUpTasks(Integer userId) {
        if(context.getTaskCache().isAvailable()) {
            context.getTaskCache().deleteUserTask(userId,this.getClazz());
        }
        if(context.getConfig().isStrictModel()) {
            context.getTaskService().giveUpTasks(userId);
        }
    }

    @Override
    public void addTask(Task t) {
        if (context.getTaskCache().isAvailable()) {
            TaskQueue<Task> tq = new TaskQueue<Task>(this, t);
            if (!tq.push(t)) {
                eventHandler.handle(new Event<Task>(t, EventType.TasksBackUp.getCode()));
            }
        }
        context.getTaskService().addTask(t);
    }


    @Override
    public void recycle(TaskFrame taskFrame) {
        if(context.getTaskCache().isAvailable()) {
            context.getTaskCache().deleteUserTask(taskFrame.getStaffNo(),this.getClazz());
            context.getTaskCache().getQueue(taskFrame.getSrcQueue()).pushAll(taskFrame.getData());
        }
        if(context.getConfig().isStrictModel()) {
            context.getTaskService().unlockTasks(taskFrame.getData());
        }
    }

    @Override
    public List<TaskFrame> allUserTasks(String token) {
        return  context.getTaskCache().getAllUserTasks(token);
    }

}
