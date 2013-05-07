package com.jd.bluedragon.sep.task.manager;

import com.jd.bluedragon.sep.task.Task;
import com.jd.bluedragon.sep.task.TaskFrame;
import com.jd.bluedragon.sep.task.cache.CacheClient;
import com.jd.bluedragon.sep.task.cache.KeyPrefixGen;
import com.jd.bluedragon.sep.task.cache.collection.RingCacheQueue;
import com.jd.bluedragon.sep.task.cache.TaskCache;
import com.jd.bluedragon.sep.task.event.DefaultEventHandler;
import com.jd.bluedragon.sep.task.event.EventHandler;
import com.jd.bluedragon.sep.task.event.listener.Listener;
import com.jd.bluedragon.sep.task.recycle.GlobalRecycleWorker;
import com.jd.bluedragon.sep.task.service.TaskService;
import com.jd.bluedragon.sep.task.util.PrefixUtil;

import java.util.List;

/**
 * 人工预分拣任务管理器
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-15
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
public abstract class TaskManager {

    /**
     * 缓存客户端
     */
    private CacheClient cacheClient;
    /**
     * 事件处理器
     */
    protected final EventHandler eventHandler;
    /**
     * 上下文
     */
    protected final TMContext context;
    /**
     * 缓存key值生成工具类
     */
    private final KeyPrefixGen gen;
    /**
     * 服务器总数
     */
    private Integer serverNum = 1;
    /**
     * 队列中缓存的帧数
     */
    private Integer queueLen = 10;
    /**
     * 每个任务帧包含的任务数量
     */
    private Integer frameSize = 10;
    /**
     * TODO  what's this?!!
     */
    private Integer recycleTime = -1;
    /**
     * 控制缓存加载频率，当任务队列中任务数量与queueLen的比值小于loadFactor时，
     * 任务队列会尝试调用TaskService的getTask方法来填充队列
     */
    private double loadFactor = 0.2;
    /**
     * 是否以严格模式运行。当strict为true时，任务管理器时刻会频繁访问数据来校验
     * 缓存中的数据是否与数据库一致
     */
    private boolean strict = false;
    /**
     * 任务缓存对象
     */
    private TaskCache taskCache;
    /**
     * 任务服务接口，用于缓存与DB的交互
     */
    private TaskService taskService;
    /**
     *把任务对象存入缓存时采取的序列化方式，默认是JSON
     */
    private String dataType;
    /**
     * 任务管理器名称
     */
    private String name;
    /**
     * 任务回收Worker 用于回收超时未完成的任务
     */
    private GlobalRecycleWorker globalRecycleWorker;
    /**
     * 任务类
     */
    private Task  taskBean;
    /**
     * 任务类Class
     */
    private Class<? extends Task> clazz;

    private String token;   //不用配置

    public TaskManager() {
        this.context = new TMContext();
        this.eventHandler = new DefaultEventHandler();
        this.gen = new KeyPrefixGen(this.name);
    }

    /**
     * 初始化
     * 获取&生成token
     * 启动全局任务回收
     */
    public void  init() {
        RingCacheQueue<String> rq = cacheClient.getRingQueue(gen.getRQName());
        if(rq.size() < context.getConfig().getServerNum()) {
            String token = PrefixUtil.randomPrefix();
            rq.push(token);
        }
        this.token = rq.next();
        this.globalRecycleWorker = new GlobalRecycleWorker(this);
    }

    public CacheClient getCacheClient() {
        return cacheClient;
    }


    public KeyPrefixGen getGen() {
        return gen;
    }

    public void setCacheClient(CacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    public void setQueueLen(Integer queueLen) {
        this.context.getConfig().setQueueLen(queueLen);
    }

    public void setFrameSize(Integer frameSize) {
        this.context.getConfig().setFrameSize(frameSize);
    }

    public void setLoadFactor(double loadFactor) {
        this.context.getConfig().setLoadFactor(loadFactor);
    }

    public void setRecycleTime(Integer recycleTime) {
        this.context.getConfig().setRecycleTime(recycleTime);
    }

    public void setStrict(boolean strict) {
        this.context.getConfig().setStrict(strict);
    }

    public void setTaskCache(TaskCache taskCache) {
        this.context.setTaskCache(taskCache);
    }

    public void setTaskService(TaskService taskService) {
        this.context.setTaskService(taskService);
    }


    public void setDataType(String dataType) {
        this.context.getConfig().setDataType(dataType);
    }


    public void setServerNum(Integer serverNum) {
        this.context.getConfig().setServerNum(serverNum);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }


    public void setTaskBean(Task taskBean) {
        this.taskBean = taskBean;
        this.clazz = taskBean.getClass();
    }

    public Class<? extends Task> getClazz() {
        return clazz;
    }


    /**
     * 获取任务管理器上下文
     *
     * @return TMContext
     */
    public TMContext getContext() {
        return context;
    }


    private List<Listener> listeners;


    /**
     * 设置监听器
     *
     * @param listeners 监听器列表
     */
    public void setListeners(List<Listener> listeners) {
        this.listeners = listeners;
        for (Listener listener : listeners) {
            eventHandler.register(listener);
        }
    }

    /**
     * 统计任务的实际数量
     *
     * @param t 任务分类
     * @return 任务总数
     */
    public abstract long countTask(Task t);

    /**
     * 根据员工信息返回任务
     *
     * @param userId 员工标识
     * @param t      任务类型
     * @return 任务列表
     */
    public abstract List<? extends Task> getTask(Integer userId, Task t);

    /**
     * 处理任务
     *
     * @param userId 员工标识
     * @param tasks  任务列表
     * @return 成功处理的任务条数
     */
    public abstract Integer completeTasks(Integer userId, List<? extends Task> tasks);


    /**
     * 放弃用户当前的任务
     *
     * @param userId 用户ID
     */
    public abstract void giveUpTasks(Integer userId);

    /**
     * 新增任务
     *
     * @param t 任务对象
     */
    public abstract void addTask(Task t);


    /**
     * 回收任务
     *
     * @param taskFrame 用户获取到的任务
     */
    public abstract void recycle(TaskFrame taskFrame);

    /**
     * 获取所有活动的用户任务
     * @param token  令牌
     * @return 所有被用户获取到的任务
     */

    public abstract List<TaskFrame> allUserTasks(String token);


    /**
     * 获取令牌
     * @return token
     */
    public String nextToken() {
        this.token = (String) cacheClient.getRingQueue(gen.getRQName()).next();
        return token;
    }
}
