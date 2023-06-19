package org.mgd.juc;

import java.util.concurrent.*;

public class ThreadPoolT extends ThreadPoolExecutor{
    public ThreadPoolT(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(0, 1, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
        poolExecutor.execute(()->{
            System.out.println("hello");
        });
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadFactory.newThread(()->{}).start();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
    }
}
