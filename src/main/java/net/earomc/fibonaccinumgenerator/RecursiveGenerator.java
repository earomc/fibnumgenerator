package net.earomc.fibonaccinumgenerator;

import java.math.BigInteger;

public class RecursiveGenerator implements FibonacciNumberGenerator{

    private int calls = 0;
    private BigInteger[] fibCache;
    private boolean cacheInitialized = false;
    private final boolean debugInfo;
    private final StopWatch stopWatch = new StopWatch();

    public RecursiveGenerator(boolean debugInfo) {
        this.debugInfo = debugInfo;
    }

    public BigInteger gen(int n, boolean cache) {
        BigInteger fib;
        if (cache) {
            fib = gen(n);
        } else {
            fib = genUncached(n);
        }
        return fib;
    }

    @Override
    public BigInteger gen(int n) {
        if (n < 0) throw new IllegalArgumentException("n Cannot be negative. n = " + n);
        BigInteger fib;
        if (!cacheInitialized || n >= fibCache.length) {
            initEmptyFibCacheForN(n);
        }
        BigInteger cachedFib = fibCache[n];
        if (cachedFib != null) {
            return cachedFib;
        } else fib = genAndCache(n);
        calls = 0;
        return fib;
    }

    private void initEmptyFibCacheForN(int n) {
        int size = n + 1;
        fibCache = new BigInteger[size];
        if (size > 0) {
            fibCache[0] = BigInteger.ZERO;
            if (size > 1) {
                fibCache[1] = BigInteger.ONE;
            }
        }
        cacheInitialized = true;
    }

    public BigInteger genSmart(int n, boolean clearMemory) {
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;

        BigInteger[] fibs = new BigInteger[n];
        fibs[0] = BigInteger.ZERO;
        fibs[1] = BigInteger.ONE;
        for (int i = 2; i < n; i++) {
            fibs[i] = fibs[i - 1].add(fibs[i - 2]);
            if (clearMemory) fibs[i - 2] = null; // clear memory
        }
        return fibs[n - 1];
    }

    private BigInteger genAndCache(int n) {
        calls++;
        if (debugInfo)
            System.out.println("Calling cached fibonacci gen for " + n + "th fibonacci number.\n Calls:" + calls);
        BigInteger fib;
        if (n == 0) {
            fib = BigInteger.ZERO;
        } else if (n == 1) {
            fib = BigInteger.ONE;
        } else {
            fib = gen(n - 1).add(gen(n - 2));
        }
        fibCache[n] = fib;
        return fib;
    }

    private BigInteger genUncached(int n) {
        calls++;
        if (debugInfo) System.out.println("Calling fibonacci gen for " + n + "th fibonacci number.\n Calls:" + calls);
        if (n == 0) {
            return BigInteger.ZERO;
        } else if (n == 1) {
            return BigInteger.ONE;
        } else {
            return genUncached(n - 1).add(genUncached(n - 2));
        }
    }

    @Override
    public long getIteration() {
        return 0;
    }
}
