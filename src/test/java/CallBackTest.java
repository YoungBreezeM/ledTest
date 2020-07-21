import com.fw.domain.Callback;
import com.fw.domain.Printer;

public class CallBackTest {
    public static void main(String[] args) {
        Printer printer = new Printer();

        Callback callback = msg -> System.out.println("打印机说" + msg);

        System.out.println("要打印"+"hello");
        new Thread(()-> printer.print(callback,"hello")).start();
        System.out.println("等待打印");
    }
}
