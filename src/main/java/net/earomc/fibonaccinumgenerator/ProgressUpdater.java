package net.earomc.fibonaccinumgenerator;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProgressUpdater {

    private final FibonacciNumberGenerator generator;
    private ScheduledExecutorService scheduledExecutorService;
    private volatile float previousProgressPercentage = 0;

    public ProgressUpdater(FibonacciNumberGenerator generator) {
        this.generator = generator;
    }

    public void stop() {
        scheduledExecutorService.shutdownNow();
    }

    public void start() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::run, 1, 1, TimeUnit.SECONDS);
    }

    private void run() {
        float progressPercentage;
        progressPercentage = getProgressPercentage();

        float stepLength = (progressPercentage - previousProgressPercentage); // how much progress in percentage from 0-1 has been done in the time.
        float amountStepsTo100P = ((1 - progressPercentage) / stepLength);
        System.out.println("previousProgressPercentage = " + previousProgressPercentage);
        System.out.println("progressPercentage = " + progressPercentage);
        System.out.println("stepLength = " + stepLength);
        System.out.println("amountStepsTo100P = " + amountStepsTo100P);

        Duration estimatedTimeDuration = Duration.of((long) amountStepsTo100P, ChronoUnit.SECONDS);
        System.out.println("Progress: " + formatPercentage(progressPercentage) + "% Estimated time: " + Main.formatDuration(estimatedTimeDuration));

        this.previousProgressPercentage = progressPercentage;
    }

    private String formatPercentage(float percentage) {
        return String.format("%.1f", percentage * 100f);
    }

    private float getProgressPercentage() {
        return generator.getCurrentIteration() * 1f / generator.getRequestedIteration();
    }


}
