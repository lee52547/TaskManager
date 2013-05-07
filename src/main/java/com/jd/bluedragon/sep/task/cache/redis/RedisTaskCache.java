//package com.jd.bluedragon.sep.task.cache.redis;
//
//import com.jd.bluedragon.sep.task.Task;
//import com.jd.bluedragon.sep.task.TaskFrame;
//import com.jd.bluedragon.sep.task.cache.collection.CacheQueue;
//import com.jd.bluedragon.sep.task.cache.TaskCache;
//import com.jd.bluedragon.sep.task.cache.TaskQueueAllocator;
//import com.jd.bluedragon.sep.task.util.JsonUtil;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
///**
//* 基于redis的任务缓存
//* Created with IntelliJ IDEA.
//* User: lijiale
//* Date: 13-4-18
//* Time: 上午10:19
//* To change this template use File | Settings | File Templates.
//*/
//public class RedisTaskCache extends TaskCache {
//
//    private Long failTime = null;
//
//    private RedisTemplate<Serializable, Serializable> redisTemplate;
//
//    protected RedisTaskCache(TaskQueueAllocator taskQueueAllocator) {
//        super(taskQueueAllocator);
//    }
//
//
//    /**
//     * 返回指定名称的队列 不论队列是否真的在缓存中存在
//     *
//     * @param key 队列名称
//     * @return RedisQueue
//     */
//    public <T> RedisQueue<T> getQueue(String key) {
//        return new RedisQueue<T>(key);
//    }
//
//    @Override
//    public void set(String key, Object value) {
//        Serializable s = JsonUtil.toJson(value);
//        try {
//            redisTemplate.opsForValue().set(key, s);
//            failTime = null;
//        } catch (Exception e) {
//            failTime = System.currentTimeMillis();
//        }
//    }
//
//    @Override
//    public Serializable get(String key) {
//        Serializable s = null;
//        try {
//            s = redisTemplate.opsForValue().get(key);
//            failTime = null;
//        } catch (Exception e) {
//            failTime = System.currentTimeMillis();
//        }
//        return s;
//    }
//
//    @Override
//    public boolean contains(String key) {
//        boolean val = false;
//        try {
//            val = redisTemplate.hasKey(key);
//            failTime = null;
//        } catch (Exception e) {
//            failTime = System.currentTimeMillis();
//        }
//        return val;
//    }
//
//    @Override
//    public boolean isAvailable() {
//        return failTime == null || System.currentTimeMillis() - failTime > 1000 * 60;
//    }
//
//
//    @Override
//    public void delete(String key) {
//        redisTemplate.delete(key);
//    }
//
//    @Override
//    public Set<Serializable> findByPrefix(String prefix) {
//        return redisTemplate.keys(prefix);
//    }
//
//    @Override
//    public TaskFrame getUserTask(Integer userId, Class<? extends Task> clazz) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public TaskFrame addUserTask(Integer userId, List<? extends Task> tasks, String srcQueue) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public void deleteUserTask(Integer userId) {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    /**
//     * 内部类 redis队列
//     */
//    class RedisQueue<T> implements CacheQueue {
//
//        private String name;
//
//        private RedisQueue(String name) {
//            this.name = name;
//        }
//
//        public List<T> shift(Integer n) {
//            List<T> val = new ArrayList<T>();
//            List<Serializable> sList = null;
//            try {
//                sList = redisTemplate.opsForList().range(name, 0, n);
//                if (sList.size() > 0) {
//                    for (Serializable s : sList) {
//                        if (s != null) {
//                            T t = (T) JsonUtil.jsonToBean(s.toString(), this.getClass().getComponentType());
//                            val.add(t);
//                        }
//                    }
//                }
//                failTime = null;
//            } catch (Exception e) {
//                failTime = System.currentTimeMillis();
//            }
//            return val;
//        }
//
//        @Override
//        public void push(Object o) {
//            Serializable s = JsonUtil.toJson(o);
//            try {
//                redisTemplate.opsForList().rightPush(name, s);
//                failTime = null;
//            } catch (Exception e) {
//                failTime = System.currentTimeMillis();
//            }
//        }
//
//        @Override
//        public long size() {
//            long val = 0;
//            try {
//                val = redisTemplate.opsForList().size(name);
//                failTime = null;
//            } catch (Exception e) {
//                failTime = System.currentTimeMillis();
//            }
//            return val;
//        }
//
//        /**
//         * 获取队列的名字
//         *
//         * @return string
//         */
//        @Override
//        public String getName() {
//            return this.name;
//        }
//
//        @Override
//        public void pushList(List dbData, int less) {
//            for (int i = less; i < dbData.size(); i++) {
//               push(dbData.get(i));
//            }
//        }
//
//        @Override
//        public void pushAll(List data) {
//            pushList(data,0);
//        }
//
//
//
//    }
//}
