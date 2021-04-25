package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
//        int[] spx = new int[]{1,2,4,2,3,9,15,1,3,9};
//        int[] mitk = new int[]{4,7,4,5,19,8,9,3,5,6,8};
//        int[] nflx = new int[]{2,8,5,3,5,7,3,1,3,3};
        double[] c = new double[]{45.88,43.68,43.03,43.61,43.19,42.90,43.52,43.01,43.95,43.95,43.26,41.88,41.13,41.66,41.42,42.21,43.53,42.08,43.07,42.71,47.64,47.65,48.93,48.28,48.66,50.40,50.73};
        double[] alb = new double[]{94.94,95.14,94.59,94.35,93.42,92.57,93.56,95.87,94.96,96.48,94.48,95.18,90.78,93.77,93.21,96.84,97.62,93.43,105.50,112.22,116.73,117.93,118.57,118.64,122.48,128.92,127.95};

        A a = new A();
//        a.toString("spx", spx);
//        a.toString("mitk", mitk);
//        a.toString("nflx", nflx);
        a.toString("c", c);
        a.toString("alb", alb);
    }
}

class A {
// Up
// Consolidate -> Linked to Passive
// Down

// Extreme (4x average)
// Strong (2x average)
// Passive -> Linked to Consolidate (If it is within the body of previous price)
// Weak (Less than average)

    public void classify(double[] prices) throws Exception {
        List<Double> numericalChanges = getChanges(prices);
        List<Double> percentageChanges = getChangesInPercent(prices);
        double averagePercentageChange = getAverageChangeInPercent(percentageChanges);
        for (int i = 0; i < prices.length - 1; i++) {
            if (percentageChanges.get(i) > averagePercentageChange * 3.0) {
                System.out.print("Extreme ");
            } else if (percentageChanges.get(i) > averagePercentageChange * 1.5) {
                System.out.print("Strong ");
            } else if (percentageChanges.get(i) < averagePercentageChange * 0.25) {
                System.out.print("Standstill ");
            } else {
                System.out.print("Weak ");
            }
            if (prices[i + 1] > prices[i]) {
                System.out.print("Up");
            } else if (prices[i + 1] < prices[i]) {
                System.out.print("Down");
            } else {
                System.out.print("Even");
            }
            System.out.println();
        }
    }

    public double getAverageChangeInPercent(List<Double> changesInPercent) throws Exception {
        return getAveragePriceChanges(changesInPercent);
    }

    public double getAveragePriceChanges(List<Double> changes) throws Exception {
        if (changes == null || changes.size() <= 0) throw new Exception("Changes cannot be empty");

        double sum = 0.0;
        int i;
        for (i = 0; i < changes.size(); i++) {
            sum += Math.abs(changes.get(i));
        }
        return sum / i;
    }

    public List<Double> getChangesInPercent(double[] prices) {
        List<Double> changes = new ArrayList<>();
        for (int i = 0; i < prices.length - 1; i++) {
            double difference = prices[i + 1] - prices[i];
            double average = Math.abs((difference / prices[i]) * 100);
            changes.add(average);
        }
        return changes;
    }

    public double getAveragePriceChange(List<Double> changes) throws Exception {
        if (changes == null || changes.size() <= 0) throw new Exception("Changes cannot be empty");

        double sum = 0.0;
        int i;
        for (i = 0; i < changes.size(); i++) {
            sum += Math.abs(changes.get(i));
        }
        return sum / i;
    }

    public List<Double> getChanges(double[] prices) {
        List<Double> changes = new ArrayList<>();
        for (int i = 0; i < prices.length - 1; i++) {
            changes.add(prices[i + 1] - prices[i]);
        }
        return changes;
    }

    public double largestIncrease(List<Double> changes) throws Exception {
        if (changes == null || changes.size() <= 0) throw new Exception("Changes cannot be empty");

        boolean hasChanged = false;
        double max = 0;
        for (double change : changes) {
            if (change > max) {
                max = change;
                hasChanged = true;
            }
        }

        return hasChanged ? max : 0;
    }

    public double smallestIncrease(List<Double> changes) throws Exception {
        if (changes == null || changes.size() <= 0) throw new Exception("Changes cannot be empty");

        boolean hasChanged = false;
        double min = Double.MAX_VALUE;
        for (double change : changes) {
            if (change > 0 && change < min) {
                min = change;
                hasChanged = true;
            }
        }

        return hasChanged ? min : 0;
    }

    public double largestTotalChange(List<Double> changes) throws Exception {
        if (changes == null || changes.size() <= 0) throw new Exception("Changes cannot be empty");

        boolean hasChanged = false;
        double max = 0;
        for (double change : changes) {
            double absValue = Math.abs(change);
            if (absValue > max) {
                max = absValue;
                hasChanged = true;
            }
        }

        return hasChanged ? max : 0;
    }

    public void describe(List<Double> numericalChanges, List<Double> percentageChanges) throws Exception {
        System.out.println("Average Price Change (Percentage): " + getAverageChangeInPercent(percentageChanges));
        System.out.println("Smallest Increase (Numerical): " + smallestIncrease(numericalChanges));
        System.out.println("Largest Increase (Numerical): " + largestIncrease(numericalChanges));
        System.out.println("Largest Change (Numerical): " + largestTotalChange(numericalChanges));
        System.out.println("Average Change (Numerical): " + getAveragePriceChange(numericalChanges));
    }

    public void toString(String symbol, double[] prices) throws Exception {
        System.out.println(symbol);
        classify(prices);
        for (double i : getChanges(prices)) {
            System.out.print(i + ",");
        }
        System.out.println();
        for (double i : getChangesInPercent(prices)) {
            System.out.print(i + ",");
        }
        System.out.println();
        describe(getChanges(prices), getChangesInPercent(prices));
        System.out.println("----------------");
    }
}
