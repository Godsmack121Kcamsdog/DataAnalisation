package dataAnalisation.part1.sample.models;

import dataAnalisation.part1.sample.Main;

import java.util.ArrayList;

public class UnchangedСharacteristicRow {

    private final Double value_1;
    private final Double sq_conc;
    private ArrayList<String> truthList;

    public UnchangedСharacteristicRow(Double value, Double sq_conc, Double[] truth_interval) {
        this.value_1 = value;
        this.sq_conc = sq_conc;
        truthList = new ArrayList<>();
        if (truth_interval != null){
            truthList.add(Main.round(truth_interval[0],4).toString());
            truthList.add(Main.round(truth_interval[1],4).toString());
        }
        else{
            truthList.add(" - ");
        }
    }


    public Double getValue_1() {
        return Main.round(value_1,4);
    }

    public String getSq_conc() {
        return (sq_conc == 0.0) ? "[ - ]" : Main.round(sq_conc,4).toString();
    }

    public ArrayList<String> getTruthList() {
        return truthList;
    }
}
