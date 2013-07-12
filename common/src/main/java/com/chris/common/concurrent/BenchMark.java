package com.chris.common.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;//bitsFlow

public class BenchMark {

    public volatile long totalTime;

    private CountDownLatch latch;

    private final int loop;

    private final int threads;

    private final float ratio;

    public BenchMark(final int loop, final int threads, final float ratio) {
        super();
        this.loop = loop;
        this.threads = threads;
        this.ratio = ratio;
    }

    class BenchMarkRunnable implements Runnable {

        private final MapWrapper mapWrapper;

        private final int size;

        public void benchmarkRandomReadPut(final MapWrapper mapWrapper, final int loop) {
            final Random random = new Random();
            int writeTime = 0;
            for (int i = 0; i < loop; i++) {
                final int n = random.nextInt(size);
                if (mapWrapper.get(n) == null) {
                    mapWrapper.put(n, n);
                    writeTime++;
                }
            }
        }

        public BenchMarkRunnable(final MapWrapper mapWrapper, final int size) {
            this.mapWrapper = mapWrapper;
            this.size = size;
        }

        @Override
        public void run() {
            final long start = System.currentTimeMillis();
            benchmarkRandomReadPut(mapWrapper, loop);
            final long end = System.currentTimeMillis();
            totalTime += end - start;
            latch.countDown();
        }

    }

    public void benchmark(final MapWrapper mapWrapper) {
        final float size = loop * threads * ratio;
        totalTime = 0;
        for (int k = 0; k < 3; k++) {
            latch = new CountDownLatch(threads);
            for (int i = 0; i < threads; i++) {
                new Thread(new BenchMarkRunnable(mapWrapper, (int) size)).start();
            }
            try {
                latch.await();
            } catch (final Exception e) {
                e.printStackTrace();
            }
            mapWrapper.clear();
            Runtime.getRuntime().gc();
            Runtime.getRuntime().runFinalization();
            try {
                Thread.sleep(1000);
            } catch (final Exception e) {
            }
        }
        final int rwratio = (int) (1.0 / ratio);
        System.out.println("[" + mapWrapper.getName() + "]threadnum[" + threads + "]ratio[" + rwratio + "]avgtime[" + totalTime / 3 + "]");
    }

    public static void benchmark2(final int loop, final int threads, final float ratio) {
        final BenchMark benchMark = new BenchMark(loop, threads, ratio);
        final MapWrapper[] wrappers = new MapWrapper[]{
            new HashTableMapWrapper(),
            new SyncMapWrapper(),
            new LockMapWrapper(),
            new RWLockMapWrapper(),
            new ConcurrentMapWrapper(),
            new WriteLockMapWrapper(),
        };
        for (final MapWrapper wrapper : wrappers) {
            benchMark.benchmark(wrapper);
        }
    }

    public static void test() {
        benchmark2(1000000, 10, 1);//r:w 1:1
        benchmark2(1000000, 10, 0.1f);//r:w 10:1
        benchmark2(1000000, 10, 0.01f);//r:w 100:1
        benchmark2(1000000, 10, 0.001f);//r:w 1000:1
        /////
        benchmark2(1000000, 50, 0.1f);//r:w 10:1
        benchmark2(1000000, 50, 0.01f);//r:w 100:1
        benchmark2(1000000, 50, 0.001f);//r:w 1000:1
        /////
        benchmark2(100000, 100, 1f);//r:w 10:1
        benchmark2(100000, 100, 0.1f);//r:w 10:1
        benchmark2(100000, 100, 0.01f);//r:w 100:1
        benchmark2(100000, 100, 0.001f);//r:w 1000:1
        ////
    }

    public static void main(final String[] args) {
        test();
    }
}
