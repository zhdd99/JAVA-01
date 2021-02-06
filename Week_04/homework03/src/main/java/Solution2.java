import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author zhdd99
 */
public class Solution2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start=System.currentTimeMillis();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> f = executorService.submit(Solution2::sum);
        System.out.println("异步计算结果为："+f.get());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        executorService.shutdown();
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
