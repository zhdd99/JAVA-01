import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class Solution9 {

    public static void main(String[] args) throws ExecutionException {
        long start=System.currentTimeMillis();
        Object l = new Object();
        AtomicInteger result = new AtomicInteger();
        final Thread mainThread = Thread.currentThread();
        Thread thread = new Thread(() -> {
            result.set(sum());
            LockSupport.unpark(mainThread);
        });
        thread.start();
        LockSupport.park();
        System.out.println("异步计算结果为："+result.get());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    private static int sum() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
