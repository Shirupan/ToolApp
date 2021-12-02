package com.slaoren.test;

public class TestRunnable implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1000*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
