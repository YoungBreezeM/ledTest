import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();

        ScheduledFuture<?> schedule = service.scheduleAtFixedRate(() ->
                System.out.println(System.currentTimeMillis()),
                0,1000, TimeUnit.MILLISECONDS);

    }
}
