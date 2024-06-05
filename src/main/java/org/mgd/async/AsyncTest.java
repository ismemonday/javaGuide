package org.mgd.async;

import java.util.concurrent.CompletableFuture;


public class AsyncTest {
    private String name;

    public static void main(String[] args) {
        CompletableFuture<Void> chainFuture = CompletableFuture.runAsync(() -> {
            throw new RuntimeException("runTimeException");
        });
        chainFuture.whenComplete((v, e) -> {
            System.out.println(v);
            System.out.println(e);

        });
    }


    public static void testComplete() {
        CompletableFuture<Object> futureChain = CompletableFuture.completedFuture(null);
        System.out.println("start");
        futureChain.thenCompose((v) -> {
            AsyncTest asyncTest = new AsyncTest();
            new Thread(() -> {
                System.out.println(v);
                System.out.println("thenCompose1");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            return CompletableFuture.completedFuture(asyncTest);
        });
        futureChain.thenCompose((v) -> {
            System.out.println(v);
            System.out.println("thenCompose2");
            return CompletableFuture.completedFuture(null);
        });
        System.out.println("end");
    }
}
