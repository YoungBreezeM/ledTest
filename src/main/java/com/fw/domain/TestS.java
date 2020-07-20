package com.fw.domain;

import java.util.concurrent.CountDownLatch;

public class TestS implements Runnable {

    private CountDownLatch countDownLatch;

    public TestS(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        int num = 0;
        while (num<100){
            num++;
            try {
                Thread.sleep(100);
                System.out.println(num);

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }

        }
    }
}
