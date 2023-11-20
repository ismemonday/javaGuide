package org.mgd.juc;


import java.util.concurrent.*;

public class ReadTimeOut {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            System.out.println("当前线程" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("运行时错误");
        });
        new Thread(task).start();

        Integer integer = task.get(18, TimeUnit.SECONDS);
        System.out.println(integer);

        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.add(1);
        queue.add(2);
        System.out.println("1");
        boolean offer = queue.offer(3,3,TimeUnit.SECONDS);
    }
}
