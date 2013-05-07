package com.jd.bluedragon.sep.task.test;

import com.jd.bluedragon.sep.task.util.PrefixUtil;

import java.util.*;
import java.util.concurrent.*;

/**
 * 测试类--模拟集群启动
 */
public class MultiTMgrModelTest {
    static int config = 5;
    static Set keySet = new HashSet();
    static Queue a = new ArrayBlockingQueue(5);
    static Queue b = new ArrayBlockingQueue(5);
    static Queue c = new ArrayBlockingQueue(5);

    static class Server implements Callable<String> {
        String name;
        String pk;
        String sk;

        Server(String name) {
            this.name = name;
        }


        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            String key = null;
            if (keySet.size() < config) {
                if(b.size() == 0) {
                    pk = PrefixUtil.randomPrefix();
                    a.add(pk);
                }else {
                    pk = (String) b.poll();
                }

                keySet.add(pk);

                if(keySet.size() == config) {
                    sk = (String) a.poll();
                    for(Object k : keySet) {
                        a.add(k);
                    }

                }else{
                    sk = PrefixUtil.randomPrefix();
                }
            } else {
                System.out.println(name + "服务重启");
                pk = (String) a.poll();
                b.add(pk);
                System.out.println(name + "重新获得主key：" + pk);
                if (b.size() < 5) {
                    sk = (String) a.peek();
                } else {
                    sk = (String) b.peek();
                }
                System.out.println(name + "重新获得副key：" + sk);
                if (a.size() == 0) {
                    System.out.println("所有服务已全部重启，并且重新分配key");
                    System.out.println("开始备份key");
                    while (b.peek() != null) {
                        a.add(b.poll());
                    }
                }
            }
            return "[" + name + "]PK:" + pk + "; SK:" + sk ;
        }
    }

    static class Cluster {
        int sn = 5;

        public void start() {
            List<Future<String>> results = new ArrayList<Future<String>>();
            ExecutorService exec = Executors.newCachedThreadPool();
            for (int i = 0; i < sn; i++) {
                results.add(exec.submit(new Server("S" + i)));
            }
            System.out.println("Server's Number:" + results.size());
            for (Future<String> t : results) {
                try {
                    if(t != null) {
                        System.out.println(t.get());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            exec.shutdown();
        }


    }

    public static void main(String[] args) {

        Cluster cluster = new Cluster();
        cluster.start();
        cluster.start();
        cluster.start();

    }

}
