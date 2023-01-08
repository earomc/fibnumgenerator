package net.earomc.fibonaccinumgenerator;

import java.math.BigInteger;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class NoArrayGenerator implements FibonacciNumberGenerator {
    private volatile long index = 0;
    private long lastN;
    private volatile boolean running = false;

    @Override
    public BigInteger gen(final long n) {
        this.lastN = n;
        this.running = true;
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
        Thread thread = new Thread(this::run);
        thread.start();
        BigInteger gen = gen(n);
        this.running = false;
        return gen;
    }

    private void run() {
        float prevPercentage;
        float percentage;
        long delayTimeMillis = 1000;
        while (this.running) {
            prevPercentage = getProgressPercentage();
            try {
                Thread.sleep(delayTimeMillis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            percentage = getProgressPercentage();
            float stepLength = (percentage - prevPercentage); // how much progress in percentage from 0-1 has been done in the time.
            float amountStepsTo100P = ((1 - percentage) / stepLength);
            System.out.println("stepLength = " + stepLength);
            System.out.println("amountStepsTo100P = " + amountStepsTo100P);
            long estimatedTime = (long) (amountStepsTo100P * delayTimeMillis);
            Duration estimatedTimeDuration = Duration.of(estimatedTime, ChronoUnit.MILLIS);
            System.out.println("Progress: " + formatPercentage(percentage) + "% Estimated time: " + Main.formatDuration(estimatedTimeDuration));
        }
    }

    private String formatPercentage(float percentage) {
        return String.format("%.1f", percentage * 100f);
    }

    private float getProgressPercentage() {
        return (index * 1f / lastN);
    }

    @Override
    public long getIteration() {
        return lastN;
    }
}