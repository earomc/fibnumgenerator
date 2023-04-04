package net.earomc.fibonaccinumgenerator;

import java.math.BigInteger;


public interface FibonacciNumberGenerator {

    long[] FIBONACCIS = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55};

    BigInteger gen(long n);
    default BigInteger gen(int n) {
        return gen((long) n);
    }

    long getRequestedIteration();
    long getCurrentIteration();


}
