package net.earomc.fibonaccinumgenerator;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.Formatter;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("MaxMemory: " + (Runtime.getRuntime().maxMemory() / 1.074e+9));
        System.out.println("FreeMemory: " + Runtime.getRuntime().freeMemory() / 1.074e+9);
        System.out.println("TotalMemory: " + Runtime.getRuntime().totalMemory() / 1.074e+9);
        NoArrayGenerator generator = new NoArrayGenerator();
        while (true) {
            String s = new Scanner(System.in).nextLine();
            try {
                askForGen(generator, Integer.parseInt(s));
            } catch (NumberFormatException e) {
                System.out.println("Invalid int format.");
                continue;
            }
            if (s.equalsIgnoreCase("quit")) {
                break;
            }
        }
    }

    private static void askForGen(NoArrayGenerator generator, int n) {
        System.out.println("Generating " + englishCounter(n) + " Fibonacci Number ...");
        BigInteger fib;
        StopWatch memClearWatch = new StopWatch().start();
        //fib = generator.genSmart(n - 1, true);
        fib = generator.genAndSendUpdate(n - 1);


        printFib(fib, n - 1, false);

        Duration memClearDur = memClearWatch.stop();
        System.out.println("Took: ");
        StopWatch.printDuration(memClearDur);
    }

    private static void printFib(BigInteger fib, int i, boolean scientific) {
        System.out.println(englishCounter(i + 1) + " Fibonacci Number: " + (scientific ? formatBigIntScientific(fib) : formatBigIntCommas(fib)));
    }

    private static String englishCounter(long n) {
        long i = Math.abs(n);
        if (i == 0 || i > 3) return n + "th";
        if (i == 1) return "1st";
        if (i == 2) return "2nd";
        if (i == 3) return "3rd";

        //should never happen
        return "";
    }

    private static String formatBigIntScientific(BigInteger bigInt) {
        NumberFormat formatter = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        return formatter.format(bigInt);
    }

    private static String formatBigIntCommas(BigInteger bigInt) {
        return String.format("%, d", bigInt);
    }

    public static String formatDuration(Duration duration) {
        long millisPart = duration.toMillisPart();
        long secondsPart = duration.toSecondsPart();

        Formatter formatter = new Formatter(Locale.ENGLISH);
        String positive = formatter.format(
                "%dh %dm %.4fs",
                duration.toHoursPart(), // Hours
                duration.toMinutesPart(), // Minutes
                millisPart / 1000d + secondsPart).toString(); // Seconds
        return duration.getSeconds() < 0 ? "-" + positive : positive;
    }


    private static int[] fibonacciArray(int len) {
        int[] fibonaccis = new int[len];
        fibonaccis[0] = 0;
        fibonaccis[1] = 1;
        for (int n = 2; n < len; n++) {
            fibonaccis[n] = fibonaccis[n - 1] + fibonaccis[n - 2];
        }
        return fibonaccis;
    }

    private static int fibonacci1(int n) {
        return fibonacciArray(n)[n - 1];
    }


}
