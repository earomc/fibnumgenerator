package net.earomc.fibonaccinumgenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <a href="https://muthu.co/fast-nth-fibonacci-number-algorithm/">...</a>
 * <a href="https://www.baeldung.com/java-matrix-multiplication">...</a>
 */

public class MatrixGenerator implements FibonacciNumberGenerator {

    private final static long[][] MAT1 = new long[][]{
            {0, 1},
            {1, 1}
    };

    private final static long[][] MAT2 = new long[][]{
            {0},
            {1}
    };

    private final static long[][] MAT21 = new long[][]{
            {0,1}
    };


    @Override
    public long getIteration() {
        return 0;
    }

    private long i;


    private static long[][] genMatrix(int n) {
        return multiplyMatrices(powMatrix(MAT1, n), MAT2);
    }

    private static long[][] genMatrix1(int n) {
        return multiplyMatrices(powMatrix(MAT1, n), MAT21);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 40; i++) {
            long[][] matrix = genMatrix(i);
            System.out.println(i + ": " + Arrays.deepToString(matrix));
            System.out.println("fib for " + i + ": " + matrix[1][0]);
        }
    }

    private static long[][] powMatrix(long[][] matrix, int n) {
        for (int i = 0; i < n; i++) {
            matrix = multiplyMatrices(matrix, matrix);
        }
        return matrix;
    }

    private static long[][] multiplyMatrices(long[][] firstMatrix, long[][] secondMatrix) {
        long[][] result = new long[firstMatrix.length][secondMatrix[0].length]; // [columns of firstMatrix] [rows of secondMatric]

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }
        return result;
    }

    private static long multiplyMatricesCell(long[][] firstMatrix, long[][] secondMatrix, int row, int col) {
        int cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }
}
