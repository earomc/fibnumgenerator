package net.earomc.fibonaccinumgenerator;

import java.math.BigInteger;

public class NoArrayGenerator implements FibonacciNumberGenerator {
    private long index = 0;
    private volatile boolean running = false;
    private long requestedIteration;

    @Override
    public BigInteger gen(final long n) {
        this.requestedIteration = n;
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;
        BigInteger cur = BigInteger.ONE; // 3rd fib number (at index 2) is always 1. (0, 1, 1 <---, 2, 3, 5, ...)
        BigInteger prev = BigInteger.ONE;
        BigInteger oldCur;
        for (long i = 2; i < n; i++) {
            this.index = i;
            oldCur = cur;
            cur = cur.add(prev);
            prev = oldCur;
        }
        return cur;
    }

    public BigInteger genAndSendUpdate(long n) {

        BigInteger gen = gen(n);
        this.running = false;
        return gen;
    }

    @Override
    public long getCurrentIteration() {
        return index;
    }

    @Override
    public long getRequestedIteration() {
        return requestedIteration;
    }
}