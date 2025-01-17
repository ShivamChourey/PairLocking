package edu.vt.ece.PairLocking.set;

import edu.vt.ece.PairLocking.list.*;
import edu.vt.ece.PairLocking.bench.*;

public class Benchmark
{

    private static final String COARSELIST = "CoarseList";
    private static final String FINELIST = "FineList";
    private static final String LAZYLIST = "LazyList";
    private static final String LOCKFREELIST = "LockFreeList";
    private static final String OPTIMISTICLIST = "OptimisticList";

    public static void main(String[] args) throws Exception
    {
        String listType = (args.length <= 0 ? COARSELIST : args[0]);
        int threadCount = (args.length <= 1 ? 16 : Integer.parseInt(args[1]));
        int totalIters = (args.length <= 2 ? 64000 : Integer.parseInt(args[2]));
        int containPercentage = (args.length <= 3 ? 20 : Integer.parseInt(args[3]));
        int iters = totalIters / threadCount;

        run(args, listType, threadCount, iters, containPercentage);
    }

    private static void run(String[] args, String lockClass, int threadCount, int iters, int containPercentage) throws Exception
    {
        for (int i = 0; i < 5; i++)
        {
            ListAbstract<Integer> List = null;
            switch (lockClass.trim())
            {
                case COARSELIST:
                    List = new CoarseList<>();
                    break;
                case FINELIST:
                    List = new FineList<>();
                    break;
                case LAZYLIST:
                    List = new LazyList<>();
                    break;
                case OPTIMISTICLIST:
                    List = new OptimisticList<>();
                    break;
                case LOCKFREELIST:
                    List = new LockFreeList<>();
                    break;
            }

            final Counter counter = new SharedCounter(0, List, containPercentage);

            runNormal(counter, threadCount, iters);
        }
    }

    private static void runNormal(Counter counter, int threadCount, int iters) throws Exception
    {

        final TestThread[] threads = new TestThread[threadCount];
        TestThread.reset();

        for (int t = 0; t < threadCount; t++)
        {
            threads[t] = new TestThread(counter, iters);
        }

        for (int t = 0; t < threadCount; t++)
        {
            threads[t].start();
        }

        long totalTime = 0;
        for (int t = 0; t < threadCount; t++)
        {
            threads[t].join();
            totalTime += threads[t].getElapsedTime();
        }

        System.out.println("Average time per thread is " + totalTime / threadCount + "ms");
    }
}