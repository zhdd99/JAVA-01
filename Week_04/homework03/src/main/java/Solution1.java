import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import io.reactivex.Flowable;

/**
 * @author zhdd99
 */
public class Solution1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start=System.currentTimeMillis();
        FutureTask<Integer> f = new FutureTask<>(Solution1::sum);
        new Thread(f).start();
        System.out.println("异步计算结果为："+f.get());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

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
