import java.util.concurrent.CountDownLatch;

public class TestSocket {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(50);
        TestS testS = new TestS(countDownLatch);

        Thread thread = new Thread(testS);
        thread.start();

        countDownLatch.await();
        thread.stop();
        System.out.println("结束");

    }
}
