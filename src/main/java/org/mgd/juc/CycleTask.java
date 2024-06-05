package org.mgd.juc;

import java.util.concurrent.locks.LockSupport;

public abstract class CycleTask implements Runnable {
    protected final long sleepTime;
    protected volatile boolean terminated = false;
    private Object lock = new Object();


    protected CycleTask(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void terminate() {
        synchronized (lock) {
            terminated = true;
            lock.notifyAll();
        }
    }


    @Override
    public void run() {
        while (!terminated) {
            try {
                LockSupport.park();
                runActualTask();
                if (sleepTime > 0 && !terminated) {
                    synchronized (lock) {
                        lock.wait(sleepTime);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    abstract void runActualTask();


}
