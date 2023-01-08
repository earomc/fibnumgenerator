package net.earomc.fibonaccinumgenerator;

import java.math.BigInteger;


public interface FibonacciNumberGenerator {

    long[] FIBONACCIS = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55};

    default BigInteger gen(long n){return null;}
    default BigInteger gen(int n){return null;}

    long getIteration();
}
