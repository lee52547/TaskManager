package com.jd.bluedragon.sep.task.recycle;

import com.jd.bluedragon.sep.task.manager.TaskManager;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定期对所有用户的过期任务进行回收
 * 目前存在缺陷：多个实例时，重启之后每个实例都会查找当前所有任务进行回收
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-22
 * Time: 下午12:55
 * To change this template use File | Settings | File Templates.
 */
public class GlobalCountWorker {

    private final TaskManager taskManager;
    private final Timer gTimer;


    public GlobalCountWorker(final TaskManager taskManager) {
        this.taskManager = taskManager;
        this.gTimer = new Timer();
        init();
    }

    private void init() {
        //global recycle
        gTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        //TODO
                    }
                },new Date());

        //local recycle
        gTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //TODO
            }
        },new Date(),60 * 1000);
    }


}
