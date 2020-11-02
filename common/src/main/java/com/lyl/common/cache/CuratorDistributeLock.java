package com.lyl.common.cache;


import com.lyl.common.exception.ApplicationException;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import javax.annotation.PreDestroy;

/**
 * @ClassName CuratorDistributeLock
 * @DeScription 基于zk下curator提供分布式锁工具类
 * @Author GW00163274
 * @Date 2019/6/21 11:14
 * @Version 1.0
 */
public class CuratorDistributeLock {

    // Curator客户端
    CuratorFramework  client = null;

    /**
     * 同步创建zk示例，原生api是异步的
     * 这一步是设置重连策略
     *
     *  ExponentialBackoffRetry构造器参数：
     *  curator链接zookeeper的策略:ExponentialBackoffRetry
     *  baseSleepTimeMs：初始sleep的时间
     *  maxRetries：最大重试次数
     *  maxSleepMs：最大重试时间
     */
    public CuratorDistributeLock(String zkServerIps, Integer maxRetries, Integer baseSleepTimeMs, Integer sessionTimeoutMs) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);

        // 实例化Curator客户端，Curator的编程风格可以让我们使用方法链的形式完成客户端的实例化
        client = CuratorFrameworkFactory.builder() // 使用工厂类来建造客户端的实例对象
                .connectString(zkServerIps)  // 放入zookeeper服务器ip
                .sessionTimeoutMs(sessionTimeoutMs).retryPolicy(retryPolicy)  // 设定会话时间以及重连策略
                .build();  // 建立连接通道

        // 启动Curator客户端
        client.start();
    }

    /**bean销毁时调用的方法 关闭客户端*/
    @PreDestroy
    public void closeZKClient(){
        if (client != null) {
            this.client.close();
        }
    }

    /**
     * @Description //TODO 构建分布式锁
     * @Date 15:27 2019/6/21
     * @Param 根节点路径
     * @return org.apache.curator.framework.recipes.locks.InterProcessMutex
     **/
    public InterProcessMutex buildLock(String rootPath){
        if (this.client==null){
           throw new ApplicationException("未检测出初始化Curator客户端,请初始化");
        }
        return new InterProcessMutex(this.client, rootPath);
    }

    public static void main(String[] args) {
    }

     public static void process(InterProcessLock lock) {
        System.out.println(Thread.currentThread().getName() + " acquire");
        try {
            lock.acquire();
            System.out.println(Thread.currentThread().getName() + " acquire success");
            Thread.sleep(2000);
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " release");
            try {
                lock.release();
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " release success");
    }


}
