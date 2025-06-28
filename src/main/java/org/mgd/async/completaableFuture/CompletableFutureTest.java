package org.mgd.async.completaableFuture;

import java.util.concurrent.*;

/**
 * @Author maoguidong
 * @Date 2024/6/12
 * @Des
 */
public class CompletableFutureTest {
    public static void main(String[] args) {
        //测试timeOut
        testTimeOut();
    }

    private static void testTimeOut() {
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            System.out.println("runAsync");
            try {
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, Executors.newFixedThreadPool(5));
        runAsync.completeOnTimeout(null, 1, TimeUnit.SECONDS);
        runAsync.orTimeout(2, TimeUnit.SECONDS)
                .thenAccept(resp -> {
                    System.out.println("111");
                }).thenAccept(resp -> {
                    System.out.println("333");
                })
                .whenComplete((resp, throwable) -> {
                    System.out.println("222");
                });
    }

    private static void testRunAsync() {
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            System.out.println("runAsync");
            try {
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runAsync.complete(null);
        }).start();
//        while (!runAsync.isDone()){
//            System.out.println("------");
//            System.out.println("isDone:"+runAsync.isDone());
//            System.out.println("isCancelled:"+runAsync.isCancelled());
//            System.out.println("isCompletedExceptionally:"+runAsync.isCompletedExceptionally());
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        runAsync.whenComplete((s, throwable) -> {
            System.out.println("s:" + s);
            System.out.println("throwable:" + throwable);
        });
    }

    private static void testSupplyAsync() {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync");
            return "supplyAsync";
        });
    }
}
