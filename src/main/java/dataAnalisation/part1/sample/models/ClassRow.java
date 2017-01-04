package dataAnalisation.part1.sample.models;

import dataAnalisation.part1.sample.Main;

import java.util.ArrayList;

public class ClassRow {

    private final int number_1;
    private final int c_Frequency;
    private final Double c_RFrequency;
    private final double c_Distrib;
    private ArrayList<Double> list;

    public ClassRow(int number_1, Double[] borders, int c_Frequency, Double c_RFrequency, double c_Distrib) {
        this.number_1 = number_1;
        this.c_Frequency = c_Frequency;
        this.c_RFrequency = c_RFrequency;
        this.c_Distrib = c_Distrib;
        list = new ArrayList<>();
        list.add(Main.round(borders[0], 2));
        list.add(Main.round(borders[1], 2));
    }

    public int getNumber_1() {
        return number_1;
    }

    public ArrayList<Double> getBorders() {
        return list;
    }

    public int getC_Frequency() {
        return c_Frequency;
    }

    public double getC_RFrequency() {
        return Main.round(c_RFrequency, 2);
    }

    public double getC_Distrib() {
        return Main.round(c_Distrib, 2);
    }
}
