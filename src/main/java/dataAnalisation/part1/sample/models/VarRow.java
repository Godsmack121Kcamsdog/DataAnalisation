package dataAnalisation.part1.sample.models;

import dataAnalisation.part1.sample.Main;

public class VarRow {

    private final int number;
    private final double value;
    private final int frequency;
    private final double r_Frequency;
    private final double distrib;

    public VarRow(int number, double value, int frequency, double r_Frequency, double distrib) {
        this.number = number;
        this.value = value;
        this.frequency = frequency;
        this.r_Frequency = r_Frequency;
        this.distrib = distrib;
    }

    public int getNumber() {
        return number;
    }

    public double getValue() {
        return value;
    }

    public int getFrequency() {
        return frequency;
    }

    public double getR_Frequency() {
        return Main.round(r_Frequency, 3);
    }

    public double getDistrib() {
        return Main.round(distrib, 3);
    }
}
