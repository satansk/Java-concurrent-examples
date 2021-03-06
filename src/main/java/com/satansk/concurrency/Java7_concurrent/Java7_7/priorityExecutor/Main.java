package com.satansk.concurrency.Java7_concurrent.Java7_7.priorityExecutor;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author:  satansk
 * Date:    19:20 at 2015/7/18
 * Email:   satansk@hotmail.com
 *
 * 1. 通过传入 PriorityBlockingQueue 将 ThreadPoolExecutor 改造成基于优先级队列的 Executor
 * 2. 从输出结果来看:
 *      (1) Executor 初始有两个线程，所以前两个任务将被首先执行
 *      (2) 因为工作线程只有两个，后面提交的任务将被阻塞，在等待队列中等待执行，此时因为使用 PriorityBlockingQueue
 *          保存等待任务，所以后面的任务将根据优先级的高低来调度执行
 * 3. 使用优先级队列
 *      (1) 为了提高任务等待的合理性，为了让高优先级的任务在等待中先被执行
 *      (2) 所以未阻塞的任务不受优先级队列调度影响
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 1. 传入一个 PriorityBlockingQueue 即可将 ThreadPoolExecutor 改造成一个基于优先级队列的 ThreadPoolExecutor
         * 2. 传入的 Queue 只能保存 execute() 方法提交的 Runnable 任务
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,  // 核心容量
                2,  // 最大容量
                1,  // 线程数超过核心容量时，空闲线程的存活时间
                TimeUnit.SECONDS,
                new PriorityBlockingQueue<Runnable>()); // 保存已提交的等待执行的任务

        for (int i = 0; i < 4; i++) {
            MyPriorityTask task = new MyPriorityTask(i, "Task-" + i);
            executor.execute(task);
        }

        TimeUnit.SECONDS.sleep(1);

        for (int i = 4; i < 8; i++) {
            MyPriorityTask task = new MyPriorityTask (i, "Task-" + i);
            executor.execute(task);
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("Main: end!");
    }
}
