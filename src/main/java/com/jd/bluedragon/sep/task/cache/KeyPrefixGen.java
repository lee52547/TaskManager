package com.jd.bluedragon.sep.task.cache;

import com.jd.bluedragon.sep.task.Task;

/**
 * 缓存key值生成工具
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-23
 * Time: 下午6:47
 * To change this template use File | Settings | File Templates.
 */
public class KeyPrefixGen {

    public final static String PROCESSING = "_PROCESSING_";
    public final static String TQ_REAL_SIZE = "_TQ_REAL_SIZE_";
    public final static String TASK_QUEUE = "_TASK_QUEUE_";
    public final static String TASK_RQUEUE = "_TASK_RQUEUE_";
    public final static String RECYCLE_FLAG = "_RECYCLE_FLAG";
    public final static String RQ_NAME = "_RQ_NAME";

    private final String u_prefix;
    private final String q_prefix;
    private final String s_prefix;
    private final String r_prefix;
    private final String rq_prefix;

    public KeyPrefixGen(String prefix) {
        this.u_prefix = prefix + PROCESSING;
        this.q_prefix = prefix + TASK_QUEUE;
        this.s_prefix = prefix + TQ_REAL_SIZE;
        this.r_prefix = prefix + TASK_RQUEUE;
        this.rq_prefix = prefix + RQ_NAME;
    }

    /**
     * 获取用户任务帧的key值
     *
     * @param userId 用户ID
     * @return key
     */
    public String getUserTaskKey(Integer userId) {
        return u_prefix + userId;
    }

    /**
     * 根据查询条件获取相应队列的key
     *
     * @param task 查询条件
     * @return key
     */
    public String getQueueName(Task task) {
        return q_prefix + task.sign();
    }

    /**
     * 获取任务统计数量的key
     *
     * @param task 查询条件
     * @return key
     */
    public String getTqRealSizeName(Task task) {
        return s_prefix + task.sign();
    }

    /**
     * 获取用户任务前缀
     *
     * @return prefix
     * @param token 令牌
     */
//    public String getUTFPrefix(String token) {
//        return u_prefix + token;
//    }

    /**
     * 获取任务回收队列的key
     *
     * @param t 查询条件
     */
    public String getRQueueName(Task t) {
        return r_prefix + t.sign();
    }

    /**
     * 令牌队列key
     * @return key
     */
    public String getRQName() {
        return rq_prefix;
    }
}
