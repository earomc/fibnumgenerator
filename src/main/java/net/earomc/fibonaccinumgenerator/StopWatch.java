package net.earomc.fibonaccinumgenerator;

import java.time.Duration;
import java.time.Instant;

import static net.earomc.fibonaccinumgenerator.Main.formatDuration;

public class StopWatch {

    private Instant start;
    private Thread thread;

    public StopWatch() {

    }

    public StopWatch start() {
        this.start = Instant.now();
        return this;
    }

    public Duration stop() {
        return Duration.between(start, Instant.now());
    }

    public void stopAndPrintTime() {
        printDuration(stop());
    }

    public static void printDuration(Duration d) {
        if (d == null) return;
        System.out.println(formatDuration(d));
    }
}