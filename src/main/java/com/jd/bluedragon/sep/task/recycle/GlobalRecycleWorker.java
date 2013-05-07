package com.jd.bluedragon.sep.task.recycle;

import com.jd.bluedragon.sep.task.TaskFrame;
import com.jd.bluedragon.sep.task.manager.TaskManager;

import java.util.*;

/**
 * 定期对所有用户的过期任务进行回收
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-22
 * Time: 下午12:55
 * To change this template use File | Settings | File Templates.
 */
public class GlobalRecycleWorker {

    private final TaskManager taskManager;
    private final Timer gTimer;

    public GlobalRecycleWorker(final TaskManager taskManager) {
        this.taskManager = taskManager;
        this.gTimer = new Timer();
        init();
    }

    private void init() {

        //local recycle
        gTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //TODO
                String token = taskManager.nextToken();
                List<TaskFrame> tasks = taskManager.allUserTasks(token);
                for(TaskFrame taskFrame : tasks) {
                    if(taskFrame.isExpried()) {
                        taskManager.recycle(taskFrame);
                    }
                }
            }
        },new Date(),60 * 1000);
    }


}
