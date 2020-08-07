package com.dbses.sample.ratelimiter;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * 限流器，功能如下。
 * <br>1. 队列排队情况监控；
 * <br>2. 并发执行窗口可配；
 *
 * @author yangll
 * @version v1.0
 */
@Slf4j
public class RateLimiter<T, R> {

    /**
     * 执行队列
     */
    private volatile BlockingQueue<T> queue;

    /**
     * 执行业务名称
     */
    @Setter
    private String businessName;

    /**
     * 信号量控制核心线程数
     */
    private Semaphore semaphore;

    public RateLimiter(int queueSize) {
        this(queueSize, 10);
    }

    public RateLimiter(int queueSize, int concurrentSize) {
        queue = new LinkedBlockingQueue<>(queueSize);
        businessName = "default-business";
        semaphore = new Semaphore(concurrentSize);
    }

    public R execute(Function<T, R> task, T params) throws QueueFullException {
        try {
            queue.add(params);
        } catch (Exception e) {
            throw new QueueFullException(String.format("[%s] 队列已满!", businessName));
        }
        T paramsToExecute = queue.poll();
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new IllegalStateException(String.format("执行 [%s]错误", businessName));
        }
        R result = task.apply(paramsToExecute);
        semaphore.release();
        return result;
    }

    public String getQueueList() {
        return queue.toString();
    }

    public int getQueueSize() {
        return queue.size();
    }

}
